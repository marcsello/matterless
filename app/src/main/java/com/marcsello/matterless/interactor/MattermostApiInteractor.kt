package com.marcsello.matterless.interactor

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.marcsello.matterless.db.*
import com.marcsello.matterless.events.*
import com.marcsello.matterless.model.*
import com.marcsello.matterless.network.ChannelsApi
import com.marcsello.matterless.network.PostsApi
import com.marcsello.matterless.network.TeamsApi
import com.marcsello.matterless.network.UsersApi
import com.marcsello.matterless.ui.chat.ChatMessageData
import com.marcsello.matterless.ui.home.ChannelData
import com.marcsello.matterless.ui.home.TeamData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MattermostApiInteractor @Inject constructor(private val context: Context) {

    private val dbInstance = AppDatabase.getInstance(context)
    private lateinit var retrofit: Retrofit
    private lateinit var token: String
    private lateinit var me_id: String
    private val httpClient = OkHttpClient.Builder().build()

    val sdf = SimpleDateFormat("yyyy. MM. dd. hh:mm")

    init {
        runBlocking {
            dbInstance.postDAO().deleteAllPosts()
            dbInstance.channelDAO().deleteAllChannles()
            dbInstance.teamDAO().deleteAllTeams()
        }
    }

    fun tryAutoLogin() {
        runBlocking {
            val servers = dbInstance.serverDAO().getAllServers()

            if (servers.isEmpty()) {
                return@runBlocking
            }

            if (!servers[0].sessionToken.isNullOrEmpty()) {

                token = "Bearer " + servers[0].sessionToken!!
                retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(servers[0].address + "/api/v4/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val usersApi = retrofit.create(UsersApi::class.java)

                // Test token

                val call = usersApi.getUserInfo(token, "me")

                try {
                    val response = call.execute()

                    if (response.code() != 200) {
                        Log.println(
                            Log.INFO,
                            "MattermostApiInteractor",
                            "Couldn't autologin: ${response.code()}"
                        )
                        return@runBlocking
                    }

                    me_id = response.body()?.id!!

                } catch (e: Exception) {
                    Log.println(
                        Log.INFO,
                        "MattermostApiInteractor",
                        "Couldn't autologin: " + e.toString()
                    )
                    return@runBlocking
                }

                EventBus.getDefault().post(LoginResultEvent(true, servers[0].loginId, null))

            }
        }
    }

    fun performLogin(server: String, username: String, password: String) {
        runBlocking {
            retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(server + "/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val usersApi = retrofit.create(UsersApi::class.java)

            val call = usersApi.doLogin(LoginCredentials(username, password))

            var token_to_save = ""
            try {
                val response = call.execute()

                if (response.code() != 200) {
                    EventBus.getDefault()
                        .post(LoginResultEvent(false, username, "HTTP ${response.code()}"))
                    return@runBlocking
                }

                token_to_save = response.headers()["Token"]!!
                token = "Bearer $token_to_save" // Hope it does not catch on fire
                me_id = response.body()?.id!!

            } catch (e: Exception) {
                EventBus.getDefault()
                    .post(LoginResultEvent(false, username, e.toString()))
                return@runBlocking
            }

            val s = Server(0, server, username, password, token_to_save, null)

            launch(Dispatchers.IO) {
                dbInstance.serverDAO().insertServers(s)
            }

            EventBus.getDefault().post(LoginResultEvent(true, username, null))

        }
    }

    fun logout() {
        runBlocking {
            launch(Dispatchers.IO) {
                dbInstance.serverDAO().deleteAllServers()
                dbInstance.postDAO().deleteAllPosts()
                dbInstance.channelDAO().deleteAllChannles()
                dbInstance.teamDAO().deleteAllTeams()
                dbInstance.userDAO().deleteAllUsers()
            }
            token = ""
        }
    }

    private fun localResolveMe(userId: String): String {
        if (userId == "me") {
            return me_id
        } else {
            return userId
        }
    }

    suspend fun getUserData(userId: String): User? {
        val userIdResolved = localResolveMe(userId)
        val user = dbInstance.userDAO().getUserById(userIdResolved)

        if (user != null) {
            Log.println(
                Log.VERBOSE,
                "MattermostApi.getUserD",
                "$userId = ${user.username} Cache: HIT"
            )
            return user
        } else {
            val usersApi = retrofit.create(UsersApi::class.java)
            val call = usersApi.getUserInfo(token, userIdResolved)

            try {
                val response = call.execute()

                if (!response.isSuccessful) {
                    Log.println(
                        Log.WARN,
                        "MattermostApi.getUser",
                        "Request unsuccessful ${response.code()}"
                    )
                    return null
                }

                val user_live = response.body() ?: return null

                val user = User(
                    user_live.id!!,
                    user_live.username!!,
                    user_live.firstName!!,
                    user_live.lastName!!,
                    user_live.nickname!!,
                    user_live.roles!!
                )

                dbInstance.userDAO().insertUsers(user)

                Log.println(
                    Log.VERBOSE,
                    "MattermostApi.getUserD",
                    "$userId = ${user.username} Cache: MISS"
                )
                return user

            } catch (e: Exception) {
                Log.println(Log.WARN, "MattermostApi.getUser", e.toString())
                return null
            }

        }

    }

    fun loadUserInfo(userId: String) {
        runBlocking {

            val userIdResolved = localResolveMe(userId)

            val user = dbInstance.userDAO().getUserById(userIdResolved)

            if (user != null) {

                EventBus.getDefault().post(
                    UserInfoLoaded(
                        user.id,
                        user.username,
                        user.firstName,
                        user.lastName,
                        user.nickName,
                        user.roles
                    )
                )

            } else {

                val usersApi = retrofit.create(UsersApi::class.java)

                val call = usersApi.getUserInfo(token, userIdResolved)

                try {
                    val response = call.execute()

                    if (!response.isSuccessful) {
                        Log.println(
                            Log.WARN,
                            "MattermostApi.getUser",
                            "Request unsuccessful ${response.code()}"
                        )
                        return@runBlocking
                    }

                    val user_live = response.body() ?: return@runBlocking

                    launch(Dispatchers.IO) {
                        dbInstance.userDAO().insertUsers(
                            User(
                                user_live.id!!,
                                user_live.username!!,
                                user_live.firstName!!,
                                user_live.lastName!!,
                                user_live.nickname!!,
                                user_live.roles!!
                            )
                        )
                    }

                    EventBus.getDefault().post(
                        UserInfoLoaded(
                            user_live.id!!,
                            user_live.username!!,
                            user_live.firstName!!,
                            user_live.lastName!!,
                            user_live.nickname!!,
                            user_live.roles!!
                        )
                    )

                } catch (e: Exception) {
                    Log.println(Log.WARN, "MattermostApi.getUser", e.toString())
                    return@runBlocking
                }


            }
        }
    }

    private fun convertTeamListTeamDataArray(teams: List<Team>): ArrayList<TeamData> {
        // TODO: nemá
        val list = ArrayList<TeamData>(teams.size)
        teams.forEach {
            list.add(TeamData(it.id, it.displayName))
        }
        return list
    }

    private fun convertTeamsListTeamDataArray(teams: List<Teams>): ArrayList<TeamData> {
        // TODO: nemá
        val list = ArrayList<TeamData>(teams.size)
        teams.forEach {
            list.add(TeamData(it.id!!, it.displayName!!))
        }
        return list
    }

    fun loadTeams(userId: String) {
        runBlocking {

            val userIdResolved = localResolveMe(userId)

            val teams = dbInstance.teamDAO().getTeams()

            if (!teams.isNullOrEmpty()) {

                EventBus.getDefault().post(
                    TeamsLoadedEvent(
                        convertTeamListTeamDataArray(teams)
                    )
                )

            } else {

                val teamsApi = retrofit.create(TeamsApi::class.java)

                val call = teamsApi.getTeamsForUser(token, userIdResolved, null, null)

                try {
                    val response = call.execute()

                    if (!response.isSuccessful) {
                        Log.println(
                            Log.WARN,
                            "MattermostApi.getTeams",
                            "Request unsuccessful ${response.code()}"
                        )
                        return@runBlocking
                    }

                    val teams_live = response.body() ?: return@runBlocking

                    if (teams_live.isNullOrEmpty()) {
                        Log.println(
                            Log.WARN,
                            "MattermostApi.getTeams",
                            "Empty or null response: ${response.body().toString()}"
                        )
                        return@runBlocking
                    }

                    launch(Dispatchers.IO) {
                        teams_live.forEach {
                            dbInstance.teamDAO().insertTeams(
                                Team(it.id!!, it.displayName!!, it.name!!, it.type!!)
                            )
                        }
                    }


                    EventBus.getDefault().post(
                        TeamsLoadedEvent(
                            convertTeamsListTeamDataArray(teams_live)
                        )
                    )

                } catch (e: Exception) {
                    Log.println(Log.WARN, "MattermostApi.getTeams", e.toString())
                    return@runBlocking
                }


            }
        }
    }

    private fun convertChannelListToChannelDataArray(channels: List<Channel>): ArrayList<ChannelData> {
        // TODO: nemá
        val list = ArrayList<ChannelData>(channels.size)
        channels.forEach {
            if ((it.type == "O") or (it.type == "P")) {
                val formattedTimestamp = sdf.format(Date(it.lastPostAt))
                list.add(ChannelData(it.id, it.name, formattedTimestamp, false))
            }
        }
        return list
    }

    private fun convertChannelsListToChannelDataArray(channels: List<Channels>): ArrayList<ChannelData> {
        // TODO: nemá
        val list = ArrayList<ChannelData>(channels.size)
        channels.forEach {
            if ((it.type!! == "O") or (it.type!! == "P")) {
                val formattedTimestamp = sdf.format(Date(it.lastPostat!!))
                list.add(ChannelData(it.id!!, it.displayName!!, formattedTimestamp, false))
            }
        }
        return list
    }

    fun loadChannels(userId: String, teamId: String) {
        runBlocking {

            val userIdResolved = localResolveMe(userId)

            val channels = dbInstance.channelDAO().getRealChannelsOfTeam(teamId)


            if (!channels.isNullOrEmpty()) {

                EventBus.getDefault().post(
                    ChannelsLoadedEvent(
                        teamId,
                        convertChannelListToChannelDataArray(channels)
                    )
                )


            } else {

                val channelsApi = retrofit.create(ChannelsApi::class.java)

                val call = channelsApi.getChannelsForUser(token, userIdResolved, teamId)

                try {
                    val response = call.execute()

                    if (!response.isSuccessful) {
                        Log.println(
                            Log.WARN,
                            "MattermostApi.getChanne",
                            "Request unsuccessful ${response.code()}"
                        )
                        return@runBlocking
                    }

                    val channels_live = response.body() ?: return@runBlocking

                    if (channels_live.isNullOrEmpty()) {
                        Log.println(
                            Log.WARN,
                            "MattermostApi.getTeams",
                            "Empty or null response: ${response.body().toString()}"
                        )
                        return@runBlocking
                    }

                    launch(Dispatchers.IO) {
                        channels_live.forEach {
                            if ((it.type!! == "O") or (it.type!! == "P")) {
                                dbInstance.channelDAO().insertChannels(
                                    Channel(
                                        it.id!!,
                                        it.teamId!!,
                                        it.type!!,
                                        it.displayName!!,
                                        it.name!!,
                                        it.lastPostat!!,
                                        null
                                    )
                                )
                            }
                        }
                    }


                    EventBus.getDefault().post(
                        ChannelsLoadedEvent(
                            teamId,
                            convertChannelsListToChannelDataArray(channels_live)
                        )
                    )

                } catch (e: Exception) {
                    Log.println(Log.WARN, "MattermostApi.getChann", e.toString())
                    return@runBlocking
                }


            }


        }

    }

    private fun friendlyfyUserName(userId: String, nickname: String?, username: String?): String {
        var goodName = userId;

        if (!(nickname.isNullOrEmpty())) {
            goodName = nickname
        } else if (!(username.isNullOrEmpty())) {
            goodName = username
        }

        return goodName
    }


    private fun convertPostsMapToChatMessageDataArray(
        order: List<String>,
        posts: Map<String, Posts>
    ): ArrayList<ChatMessageData> {
        // TODO: nemá
        val list = ArrayList<ChatMessageData>(order.size)
        order.forEach {
            val post = posts[it]!!
            val formattedTimestamp = sdf.format(Date(post.createAt!!))
            val userId = post.userId!!
            var userData: User? = null

            runBlocking {
                userData = getUserData(userId)
            }

            list.add(
                ChatMessageData(
                    friendlyfyUserName(userId, userData!!.nickName, userData!!.username),
                    userId,
                    post.message!!,
                    formattedTimestamp
                )
            )
        }
        return list
    }


    private fun mikrozas(chatMessages: List<PostWithUser>): ArrayList<ChatMessageData> {
        // TODO: nemá
        val list = ArrayList<ChatMessageData>(chatMessages.size)
        chatMessages.forEach {
            val formattedTimestamp = sdf.format(Date(it.post.createAt))
            val userId = it.post.userId
            var userData: User? = it.user

            if (userData == null) {
                runBlocking {
                    userData = getUserData(userId)
                }
            }

            list.add(
                ChatMessageData(
                    friendlyfyUserName(userId, userData!!.nickName, userData!!.username),
                    userId,
                    it.post.message,
                    formattedTimestamp
                )
            )
        }
        return list
    }


    fun loadPostsInChannel(channelId: String) {
        runBlocking {

            launch(Dispatchers.IO) {
                val posts = dbInstance.postDAO().getPostsOfChannelInverted(channelId)

                if (posts.isNullOrEmpty()) {
                    return@launch
                }

                EventBus.getDefault().post(
                    PostsLoadedEvent(
                        channelId,
                        true,
                        mikrozas(posts)
                    )
                )

            }


            val postsApi = retrofit.create(PostsApi::class.java)

            val call = postsApi.getPostsInChannel(token, channelId, null, null, null, null)

            try {
                val response = call.execute()

                if (!response.isSuccessful) {
                    Log.println(
                        Log.WARN,
                        "MattermostApi.getPosts",
                        "Request unsuccessful ${response.code()}"
                    )
                    return@runBlocking
                }

                val posts_live = response.body() ?: return@runBlocking

                if (posts_live.posts.isNullOrEmpty()) {
                    Log.println(
                        Log.WARN,
                        "MattermostApi.getPosts",
                        "Empty or null response: ${response.body().toString()}"
                    )
                    return@runBlocking
                }

                launch(Dispatchers.IO) {
                    posts_live.order?.forEach {
                        dbInstance.postDAO().insertPosts(
                            Post(
                                it,
                                posts_live.posts!![it]!!.createAt!!,
                                posts_live.posts!![it]!!.userId!!,
                                posts_live.posts!![it]!!.channelId!!,
                                posts_live.posts!![it]!!.rootId,
                                posts_live.posts!![it]!!.message!!,
                                posts_live.posts!![it]!!.type!!
                            )
                        )
                    }
                }


                EventBus.getDefault().post(
                    PostsLoadedEvent(
                        channelId,
                        false,
                        convertPostsMapToChatMessageDataArray(
                            posts_live.order!!,
                            posts_live.posts!!
                        )
                    )
                )

            } catch (e: Exception) {
                Log.println(Log.WARN, "MattermostApi.getPosts", e.toString())
                return@runBlocking
            }

        }
    }


    fun createPost(channelId: String, message: String) {
        runBlocking {
            val postsApi = retrofit.create(PostsApi::class.java)
            val call = postsApi.createPost(token, NewPost(channelId, message, null))

            try {
                val response = call.execute()

                if (!response.isSuccessful) {
                    Log.println(
                        Log.WARN,
                        "MattermostApi.createPos",
                        "Request unsuccessful ${response.code()}"
                    )
                    return@runBlocking
                }

                val newPost_live = response.body() ?: return@runBlocking

                launch(Dispatchers.IO) {
                    dbInstance.postDAO().insertPosts(
                        Post(
                            newPost_live.id!!,
                            newPost_live.createAt!!,
                            newPost_live.userId!!,
                            newPost_live.channelId!!,
                            newPost_live.rootId!!,
                            newPost_live.message!!,
                            newPost_live.type!!
                        )
                    )
                }

                val sender = getUserData(newPost_live.userId!!)

                EventBus.getDefault().post(
                    PostCreatedEvent(
                        channelId,
                        ChatMessageData(
                            friendlyfyUserName(sender!!.id, sender.nickName, sender.username),
                            sender.id,
                            newPost_live.message!!,
                            sdf.format(Date(newPost_live.createAt!!))
                        )
                    )
                )


            } catch (e: Exception) {
                Log.println(Log.WARN, "MattermostApi.createPos", e.toString())
                return@runBlocking
            }

        }
    }


}
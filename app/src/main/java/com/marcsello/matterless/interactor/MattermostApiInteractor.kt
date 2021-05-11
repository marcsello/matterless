package com.marcsello.matterless.interactor

import android.content.Context
import android.util.Log
import com.marcsello.matterless.db.AppDatabase
import com.marcsello.matterless.db.Server
import com.marcsello.matterless.db.Team
import com.marcsello.matterless.db.User
import com.marcsello.matterless.events.LoginResultEvent
import com.marcsello.matterless.events.TeamsLoadedEvent
import com.marcsello.matterless.events.UserInfoLoaded
import com.marcsello.matterless.model.LoginCredentials
import com.marcsello.matterless.model.Teams
import com.marcsello.matterless.network.TeamsApi
import com.marcsello.matterless.network.UsersApi
import com.marcsello.matterless.ui.home.TeamData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

class MattermostApiInteractor @Inject constructor(private val context: Context) {

    private val dbInstance = AppDatabase.getInstance(context)
    private lateinit var retrofit: Retrofit
    private lateinit var token: String
    private val httpClient = OkHttpClient.Builder().build()

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

                // TODO: Test if token is valid

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

            try {
                val response = call.execute()

                if (response.code() != 200) {
                    EventBus.getDefault()
                        .post(LoginResultEvent(false, username, "HTTP ${response.code()}"))
                    return@runBlocking
                }

                token = "Bearer " + response.headers()["Token"]!! // Hope it does not catch on fire

            } catch (e: Exception) {
                EventBus.getDefault()
                    .post(LoginResultEvent(false, username, e.toString()))
                return@runBlocking
            }

            val s = Server(0, server, username, password, token, null)

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


    fun loadUserInfo(userId: String) {
        runBlocking {

            val user = dbInstance.userDAO().getUserById(userId)

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

                val call = usersApi.getUserInfo(token, userId)

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

    fun convertTeamListTeamDataArray(teams: List<Team>):ArrayList<TeamData> {
        // TODO: nemá
        val list = ArrayList<TeamData>(teams.size)
        teams.forEach {
            list.add(TeamData(it.id,it.name))
        }
        return list
    }

    fun convertTeamsListTeamDataArray(teams: List<Teams>):ArrayList<TeamData> {
        // TODO: nemá
        val list = ArrayList<TeamData>(teams.size)
        teams.forEach {
            list.add(TeamData(it.id!!,it.displayName!!))
        }
        return list
    }

    fun loadTeams(userId:String) {
        runBlocking {

            val teams = dbInstance.teamDAO().getTeams()

            if (!teams.isNullOrEmpty()) {

                EventBus.getDefault().post(
                    TeamsLoadedEvent(
                        convertTeamListTeamDataArray(teams)
                    )
                )

            } else {

                val teamsApi = retrofit.create(TeamsApi::class.java)

                val call = teamsApi.getTeamsForUser(token, userId, null, null)

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
                    Log.println(Log.WARN, "MattermostApi.getUser", e.toString())
                    return@runBlocking
                }


            }
        }
    }

}
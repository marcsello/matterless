package com.marcsello.matterless.interactor

import android.content.Context
import com.marcsello.matterless.db.AppDatabase
import com.marcsello.matterless.db.Server
import com.marcsello.matterless.db.Team
import com.marcsello.matterless.events.LoginResultEvent
import com.marcsello.matterless.model.LoginCredentials
import com.marcsello.matterless.network.TeamsApi
import com.marcsello.matterless.network.UsersApi
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

                token = servers[0].sessionToken!!
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

                token = response.headers()["Token"]!! // Hope it does not catch on fire

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
            dbInstance.serverDAO().deleteAllServers()
            token = ""
        }
    }

}
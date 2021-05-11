package com.marcsello.matterless.interactor

import android.content.Context
import com.marcsello.matterless.db.AppDatabase
import javax.inject.Inject

class LocalDataInteractor @Inject constructor(private val context: Context) {
    private val dbInstance = AppDatabase.getInstance(context)

    suspend fun saveLastTeamId(teamId: String) {
        dbInstance.serverDAO().storeLastTeam(teamId)
    }

    suspend fun getLastTeamId(): String? {
        val servers = dbInstance.serverDAO().getAllServers()

        if (servers.isEmpty()) {
            return null
        } else {
            return servers[0].lastOpenedTeamId
        }

    }

}
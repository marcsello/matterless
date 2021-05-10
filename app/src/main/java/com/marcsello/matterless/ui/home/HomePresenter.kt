package com.marcsello.matterless.ui.home

import android.util.Log
import com.marcsello.matterless.ui.Presenter
import com.marcsello.matterless.ui.login.LoginScreen

class HomePresenter : Presenter<HomeScreen>() {
    fun changeTeam(id: String) {
        Log.println(Log.VERBOSE, "HomePresenter", "Changing team to $id")
        // This should load all chat info
    }

    fun loadDataOnForeground() {

        Log.println(Log.VERBOSE, "HomePresenter", "Start loading of initial data...")

        // After loading teams, the activity should call change team,
        // This is to ensure, that teams won't be loaded when there is no living activity that requires this
        screen?.personalDataLoaded("Marcsello", "KSZK");

        val teams = listOf(TeamData("a", "Alma"), TeamData("b", "Barack"))

        screen?.teamsLoaded(teams, "a")

    }

    fun performLogout() {
        screen?.loggedOut()
    }
}
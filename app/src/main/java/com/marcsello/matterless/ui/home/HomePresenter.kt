package com.marcsello.matterless.ui.home

import android.util.Log
import com.marcsello.matterless.interactor.LocalDataInteractor
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomePresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor, private val localDataInteractor: LocalDataInteractor) : Presenter<HomeScreen>() {
    fun changeTeam(id: String) {
        Log.println(Log.VERBOSE, "HomePresenter", "Changing team to $id")

        runBlocking {
            launch {

                if (id == "a") {
                    screen?.channelsLoaded(
                        arrayListOf(
                            ChannelData("asd", "misc-rage", "asdasd", false),
                            ChannelData("asd2", "misc-random", "asd2", true)
                        ),id
                    )
                } else {
                    screen?.channelsLoaded(
                        arrayListOf(
                            ChannelData("asd", "misc-happy", "aaaa", true),
                            ChannelData("asd2", "kszk-general", "aaaaa", true)
                        ),id
                    )
                }

            }
            localDataInteractor.saveLastTeamId(id)
        }
    }

    fun loadDataOnStart() {

        Log.println(Log.VERBOSE, "HomePresenter", "Start loading of initial data...")

        runBlocking {

            launch {
                // After loading teams, the activity should call change team,
                // This is to ensure, that teams won't be loaded when there is no living activity that requires this
                screen?.personalDataLoaded("Marcsello", "KSZK");

                val teams = arrayListOf(TeamData("a", "Alma"), TeamData("b", "Barack"))

                screen?.teamsLoaded(teams, localDataInteractor.getLastTeamId())


            }

        }



    }

    fun performLogout() {
        screen?.loggedOut()
    }
}
package com.marcsello.matterless.ui.home

import android.util.Log
import com.marcsello.matterless.events.ChannelsLoadedEvent
import com.marcsello.matterless.events.LoginResultEvent
import com.marcsello.matterless.events.TeamsLoadedEvent
import com.marcsello.matterless.events.UserInfoLoaded
import com.marcsello.matterless.interactor.LocalDataInteractor
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executors
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val mattermostApiInteractor: MattermostApiInteractor,
    private val localDataInteractor: LocalDataInteractor
) : Presenter<HomeScreen>() {

    private var executor = Executors.newFixedThreadPool(1)

    fun changeTeam(id: String) {
        Log.println(Log.VERBOSE, "HomePresenter", "Changing team to $id")

        runBlocking {
            launch(Dispatchers.IO) {
                localDataInteractor.saveLastTeamId(id)
            }
        }

        executor.execute {
            mattermostApiInteractor.loadChannels("me", id)
        }

    }

    fun loadDataOnStart() {

        Log.println(Log.VERBOSE, "HomePresenter", "Start loading of initial data...")

        executor.execute {
            mattermostApiInteractor.loadUserInfo("me")
        }
        executor.execute {
            mattermostApiInteractor.loadTeams("me")
        }

    }

    fun performLogout() {
        mattermostApiInteractor.logout()
        screen?.loggedOut()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: TeamsLoadedEvent) {
        runBlocking {
            val last_team_id = localDataInteractor.getLastTeamId()
            screen?.teamsLoaded(event.teams, last_team_id)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: ChannelsLoadedEvent) {
        screen?.channelsLoaded(event.channels, event.teamId)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: UserInfoLoaded) {
        screen?.personalDataLoaded(event.nickname, event.roles)
    }

}
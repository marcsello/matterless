package com.marcsello.matterless.ui.profile

import com.marcsello.matterless.events.UserInfoLoaded
import com.marcsello.matterless.events.UserProfilePictureReady
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executors
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) :
    Presenter<ProfileScreen>() {

    private var executor = Executors.newFixedThreadPool(1)

    fun loadUserData(userId: String) {
        executor.execute {
            mattermostApiInteractor.loadUserInfo(userId)
        }
        executor.execute {
            mattermostApiInteractor.downloadProfilePicture(userId)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: UserInfoLoaded) {
        screen?.userDataLoaded(
            event.id,
            event.username,
            event.first_name,
            event.last_name,
            event.nickname,
            event.roles
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: UserProfilePictureReady) {
        screen?.profilePictureLoaded(event.userId, event.f)
    }

}
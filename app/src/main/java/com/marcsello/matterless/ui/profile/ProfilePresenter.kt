package com.marcsello.matterless.ui.profile

import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) :
    Presenter<ProfileScreen>() {

    fun loadUserData(userId: String) {
        runBlocking {
            launch(Dispatchers.IO) {
                screen?.userDataLoaded(
                    userId,
                    "marcsello",
                    "Marcsello",
                    "Marcsello",
                    "NETMémer",
                    "asd"
                )
            }
        }
    }

}
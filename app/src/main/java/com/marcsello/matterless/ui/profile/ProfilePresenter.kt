package com.marcsello.matterless.ui.profile

import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) : Presenter<ProfileScreen>() {

    fun loadUserData(userId:String) {
        screen?.userDataLoaded(userId, "marcsello",mattermostApiInteractor.test(),"Marcsello","Marcsello","NETMémer")
    }

}
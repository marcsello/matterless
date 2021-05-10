package com.marcsello.matterless.ui.profile

import com.marcsello.matterless.ui.Presenter

class ProfilePresenter : Presenter<ProfileScreen>() {

    fun loadUserData(userId:String) {
        screen?.userDataLoaded(userId, "marcsello","Hooves","Marcsello","Marcsello","NETMémer")
    }

}
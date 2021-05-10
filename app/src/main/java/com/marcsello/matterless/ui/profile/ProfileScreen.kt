package com.marcsello.matterless.ui.profile

interface ProfileScreen {
    fun userDataLoaded(
        id:String,
        username: String,
        firstName: String,
        lastName: String,
        nickname: String,
        roles: String
    )
}
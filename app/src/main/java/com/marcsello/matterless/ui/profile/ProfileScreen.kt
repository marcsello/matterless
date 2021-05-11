package com.marcsello.matterless.ui.profile

import java.io.File

interface ProfileScreen {
    fun userDataLoaded(
        id: String,
        username: String,
        firstName: String,
        lastName: String,
        nickname: String,
        roles: String
    )

    fun profilePictureLoaded(userId:String, f: File)
}
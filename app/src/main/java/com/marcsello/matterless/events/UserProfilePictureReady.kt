package com.marcsello.matterless.events

import java.io.File

data class UserProfilePictureReady(
    val userId:String,
    val f: File
)

package com.marcsello.matterless.events

data class LoginResultEvent(
    val success:Boolean,
    val username:String,
    val userId:String?,
    val reason:String?
)

package com.marcsello.matterless.events

data class UserInfoLoaded(
    val id:String,
    val username:String,
    val first_name:String,
    val last_name:String,
    val nickname:String,
    val roles:String
)

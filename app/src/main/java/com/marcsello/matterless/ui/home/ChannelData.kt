package com.marcsello.matterless.ui.home

data class ChannelData(
    val id:String,
    val name:String,
    val last_message:String,
    val have_unread:Boolean
)

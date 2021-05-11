package com.marcsello.matterless.events

import com.marcsello.matterless.ui.chat.ChatMessageData

data class PostsLoadedEvent(
    val channelId:String,
    val cached:Boolean, // mikrozott, vagy friss
    val posts:ArrayList<ChatMessageData>
)

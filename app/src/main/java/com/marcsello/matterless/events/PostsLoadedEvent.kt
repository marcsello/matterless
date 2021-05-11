package com.marcsello.matterless.events

import com.marcsello.matterless.ui.chat.ChatMessageData

data class PostsLoadedEvent(
    val channelId:String,
    val posts:ArrayList<ChatMessageData>
)

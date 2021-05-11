package com.marcsello.matterless.events

import com.marcsello.matterless.ui.chat.ChatMessageData

data class PostCreatedEvent(
    val channelId:String,
    val post:ChatMessageData
)

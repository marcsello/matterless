package com.marcsello.matterless.ui.chat

data class ChatMessageData(
    val senderFriendlyName:String,
    val senderId:String,
    val content:String,
    val timestamp:String
)

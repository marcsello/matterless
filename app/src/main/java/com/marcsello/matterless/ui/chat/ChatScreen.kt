package com.marcsello.matterless.ui.chat

interface ChatScreen {
    fun messagesLoaded(messages: ArrayList<ChatMessageData>, cached:Boolean, channelId:String);
    fun newMessage(message: ChatMessageData);
}
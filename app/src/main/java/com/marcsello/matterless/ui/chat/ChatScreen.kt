package com.marcsello.matterless.ui.chat

interface ChatScreen {
    fun messagesLoaded(messages: ArrayList<ChatMessageData>);
    fun newMessage(message: ChatMessageData);
}
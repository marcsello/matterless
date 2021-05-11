package com.marcsello.matterless.ui.chat

import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import javax.inject.Inject

class ChatPresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) : Presenter<ChatScreen>() {
    fun sendMessage(channel_id: String, message: String) {

        screen?.newMessage(ChatMessageData("Marcsello","marcsello", message, "Now"))
    }

    fun loadChatData(channel_id: String) {

        val messages = arrayListOf(
            ChatMessageData("Someone","a", "asd message asdasd", "Later"),
            ChatMessageData("Yoloman","b", "Yolo van fiatalok", "5 days ago")
        )

        screen?.messagesLoaded(messages)
    }
}
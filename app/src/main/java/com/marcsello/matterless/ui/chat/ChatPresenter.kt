package com.marcsello.matterless.ui.chat

import com.marcsello.matterless.events.PostCreatedEvent
import com.marcsello.matterless.events.PostsLoadedEvent
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executors
import javax.inject.Inject

class ChatPresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) :
    Presenter<ChatScreen>() {

    private var executor = Executors.newFixedThreadPool(1)

    fun sendMessage(channel_id: String, message: String) {
        executor.execute {
            mattermostApiInteractor.createPost(channel_id, message)
        }
    }

    fun loadChatData(channel_id: String) {
        executor.execute {
            mattermostApiInteractor.loadPostsInChannel(channel_id)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: PostsLoadedEvent) {
        screen?.messagesLoaded(event.posts, event.cached, event.channelId)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: PostCreatedEvent) {
        screen?.newMessage(event.post)
    }

}
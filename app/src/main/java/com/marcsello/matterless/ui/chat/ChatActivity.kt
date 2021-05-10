package com.marcsello.matterless.ui.chat

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import javax.inject.Inject


class ChatActivity : AppCompatActivity(), ChatScreen {

    @Inject
    lateinit var chatPresenter: ChatPresenter

    private val chatListAdapter = ChatListAdapter()

    private lateinit var editTextChatMessage: EditText
    private lateinit var channelId: String
    private lateinit var recyclerViewChatMessages: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        injector.inject(this)


        channelId =
            intent.getStringExtra(KEY_CHANNEL_ID)!! // Yes, we want NPE here... because this error should never happen
        title = intent.getStringExtra(KEY_CHANNEL_FRIENDLY_NAME)


        recyclerViewChatMessages = findViewById<View>(R.id.recyclerViewChatMessages) as RecyclerView
        recyclerViewChatMessages.adapter = chatListAdapter
        recyclerViewChatMessages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        editTextChatMessage = findViewById(R.id.editTextChatMessage)

        findViewById<AppCompatImageButton>(R.id.btnSendMessage).setOnClickListener {
            val message = editTextChatMessage.text.toString()
            chatPresenter.sendMessage(channelId, message)
            editTextChatMessage.setText("")
        }


        recyclerViewChatMessages.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                recyclerViewChatMessages.postDelayed({
                    recyclerViewChatMessages.smoothScrollToPosition(
                        (recyclerViewChatMessages.adapter?.itemCount ?: 1) - 1
                    )
                }, 100)
            }
        }


    }


    override fun messagesLoaded(messages: ArrayList<ChatMessageData>) {
        chatListAdapter.setContents(messages)
        recyclerViewChatMessages.scrollToPosition(chatListAdapter.itemCount - 1);
    }

    override fun newMessage(message: ChatMessageData) {
        chatListAdapter.addNewItem(message)
        recyclerViewChatMessages.scrollToPosition(chatListAdapter.itemCount - 1);
    }

    override fun onStart() {
        super.onStart()
        chatPresenter.attachScreen(this)

        // Start loading data
        chatPresenter.loadChatData(channelId)
    }

    override fun onStop() {
        super.onStop()
        chatPresenter.detachScreen()
    }


    companion object {
        const val KEY_CHANNEL_ID = "KEY_CHANNEL_ID"
        const val KEY_CHANNEL_FRIENDLY_NAME = "KEY_CHANNEL_FRIENDLY_NAME"
        const val KEY_IS_THREAD = "KEY_IS_THREAD"
        const val KEY_THREAD_ID = "KEY_THREAD_ID"
    }

}
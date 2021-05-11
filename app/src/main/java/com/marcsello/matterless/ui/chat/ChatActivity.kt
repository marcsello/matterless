package com.marcsello.matterless.ui.chat

import android.os.Bundle
import android.util.Log
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

    private var dataIsLive = false

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
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        editTextChatMessage = findViewById(R.id.editTextChatMessage)

        findViewById<AppCompatImageButton>(R.id.btnSendMessage).setOnClickListener {
            val message = editTextChatMessage.text.toString()
            if (!message.isNullOrEmpty()) {
                chatPresenter.sendMessage(channelId, message)
                editTextChatMessage.setText("")
            }
        }


        recyclerViewChatMessages.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                recyclerViewChatMessages.postDelayed({
                    recyclerViewChatMessages.smoothScrollToPosition(
                        0
                    )
                }, 100)
            }
        }


    }


    override fun messagesLoaded(
        messages: ArrayList<ChatMessageData>,
        cached: Boolean,
        channelId: String
    ) {
        if (channelId == this.channelId) {

            if (dataIsLive and cached) {
                // A betöltött data újabb, szóval ez nemkell
                return
            }

            Log.println(
                Log.VERBOSE,
                "ChatActivity.messagesLo",
                "Betoltott uzenetek cachelve: $cached"
            )
            chatListAdapter.setContents(messages)
            //recyclerViewChatMessages.scrollToPosition(0);
            dataIsLive = !cached
        }
    }

    override fun newMessage(message: ChatMessageData) {
        chatListAdapter.addNewItem(message)
        recyclerViewChatMessages.scrollToPosition(0);
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
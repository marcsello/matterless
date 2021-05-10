package com.marcsello.matterless.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcsello.matterless.R
import com.marcsello.matterless.ui.home.ChannelListAdapter

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        title = intent.getStringExtra(KEY_CHANNEL_ID)
    }


    companion object {
        const val KEY_CHANNEL_ID = "KEY_CHANNEL_ID"
        const val KEY_IS_THREAD = "KEY_IS_THREAD"
        const val KEY_THREAD_ID = "KEY_THREAD_ID"
    }

}
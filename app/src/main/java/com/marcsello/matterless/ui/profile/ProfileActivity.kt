package com.marcsello.matterless.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcsello.matterless.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = intent.getStringExtra(KEY_USER_FRIENDLY_NAME)
    }


    companion object {
        const val KEY_CHANNEL_ID = "KEY_USER_ID"
        const val KEY_USER_FRIENDLY_NAME = "KEY_USER_FRIENDLY_NAME"
    }
}
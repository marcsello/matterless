package com.marcsello.matterless.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import javax.inject.Inject

class ProfileActivity : AppCompatActivity(), ProfileScreen {


    @Inject
    lateinit var profilePresenter: ProfilePresenter

    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        injector.inject(this)

        title = intent.getStringExtra(KEY_USER_FRIENDLY_NAME)
        userId = intent.getStringExtra(KEY_USER_ID)!! // Null pointer exception enabled

    }

    override fun onStart() {
        super.onStart()
        profilePresenter.attachScreen(this)
        profilePresenter.loadUserData(userId)
    }

    override fun onStop() {
        super.onStop()
        profilePresenter.detachScreen()
    }

    override fun userDataLoaded(
        id:String,
        username: String,
        firstName: String,
        lastName: String,
        nickname: String,
        roles: String
    ) {
        if (id == userId) {
            findViewById<TextView>(R.id.textViewUserName).text = "$firstName $lastName"
            findViewById<TextView>(R.id.textViewUserTitle).text = roles
        }
    }

    companion object {
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USER_FRIENDLY_NAME = "KEY_USER_FRIENDLY_NAME"
    }

}
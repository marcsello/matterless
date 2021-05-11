package com.marcsello.matterless.ui.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import java.io.File
import javax.inject.Inject


class ProfileActivity : AppCompatActivity(), ProfileScreen {


    @Inject
    lateinit var profilePresenter: ProfilePresenter

    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        injector.inject(this)


        userId = intent.getStringExtra(KEY_USER_ID)!! // Null pointer exception enabled
        title = "Loading..."

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
            findViewById<TextView>(R.id.textViewUserFullName).text = "$firstName $lastName"
            findViewById<TextView>(R.id.textViewNickname).text = nickname
            title = username
            findViewById<TextView>(R.id.textViewRoles).text = "Roles: $roles"
            findViewById<TextView>(R.id.textViewUserId).text = "Internal ID: $id"
        }
    }

    override fun profilePictureLoaded(f: File) {
        if (f.exists()) {
            val profileBitmap = BitmapFactory.decodeFile(f.absolutePath)
            val imageProfile: ImageView = findViewById<View>(R.id.imageProfile) as ImageView
            imageProfile.setImageBitmap(profileBitmap)
        }
    }

    companion object {
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USER_FRIENDLY_NAME = "KEY_USER_FRIENDLY_NAME"
    }

}
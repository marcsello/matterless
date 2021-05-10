package com.marcsello.matterless

import com.marcsello.matterless.ui.UIModule
import com.marcsello.matterless.ui.chat.ChatActivity
import com.marcsello.matterless.ui.home.HomeActivity
import com.marcsello.matterless.ui.login.LoginActivity
import com.marcsello.matterless.ui.profile.ProfileActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UIModule::class])
interface MatterlessApplicationComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(chatActivity: ChatActivity)
    fun inject(chatActivity: ProfileActivity)
}
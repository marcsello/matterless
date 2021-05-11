package com.marcsello.matterless

import com.marcsello.matterless.interactor.InteractorModule
import com.marcsello.matterless.ui.UIModule
import com.marcsello.matterless.ui.chat.ChatActivity
import com.marcsello.matterless.ui.chat.ChatPresenter
import com.marcsello.matterless.ui.home.HomeActivity
import com.marcsello.matterless.ui.home.HomePresenter
import com.marcsello.matterless.ui.login.LoginActivity
import com.marcsello.matterless.ui.login.LoginPresenter
import com.marcsello.matterless.ui.profile.ProfileActivity
import com.marcsello.matterless.ui.profile.ProfilePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UIModule::class, InteractorModule::class])
interface MatterlessApplicationComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(chatActivity: ChatActivity)
    fun inject(profileActivity: ProfileActivity)

    fun inject(chatPresenter: ChatPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(profilePresenter: ProfilePresenter)
}
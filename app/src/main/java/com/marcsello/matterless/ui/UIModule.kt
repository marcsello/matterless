package com.marcsello.matterless.ui

import android.content.Context
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.chat.ChatPresenter
import com.marcsello.matterless.ui.home.HomePresenter
import com.marcsello.matterless.ui.login.LoginPresenter
import com.marcsello.matterless.ui.profile.ProfilePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun loginPresenter(mattermostApiInteractor: MattermostApiInteractor) =
        LoginPresenter(mattermostApiInteractor)

    @Provides
    @Singleton
    fun homePresenter(mattermostApiInteractor: MattermostApiInteractor) =
        HomePresenter(mattermostApiInteractor)

    @Provides
    @Singleton
    fun chatPresenter(mattermostApiInteractor: MattermostApiInteractor) =
        ChatPresenter(mattermostApiInteractor)

    @Provides
    @Singleton
    fun profilePresenter(mattermostApiInteractor: MattermostApiInteractor) =
        ProfilePresenter(mattermostApiInteractor)


}

package com.marcsello.matterless.ui

import android.content.Context
import com.marcsello.matterless.ui.home.HomePresenter
import com.marcsello.matterless.ui.login.LoginPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun loginPresenter() = LoginPresenter()

    @Provides
    @Singleton
    fun homePresenter() = HomePresenter()

}

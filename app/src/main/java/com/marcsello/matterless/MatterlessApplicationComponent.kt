package com.marcsello.matterless

import com.marcsello.matterless.ui.UIModule
import com.marcsello.matterless.ui.home.HomeActivity
import com.marcsello.matterless.ui.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UIModule::class])
interface MatterlessApplicationComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
}
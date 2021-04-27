package com.marcsello.matterless

import android.app.Application
import com.marcsello.matterless.ui.UIModule

class MatterlessApplication: Application() {
    lateinit var injector: MatterlessApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerMatterlessApplicationComponent.builder().uIModule(UIModule(this)).build()
    }
}
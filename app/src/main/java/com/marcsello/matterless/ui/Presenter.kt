package com.marcsello.matterless.ui

import org.greenrobot.eventbus.EventBus

abstract class Presenter<S> {
    protected var screen: S? = null

    open fun attachScreen(screen: S) {
        this.screen = screen
        EventBus.getDefault().register(this);
    }

    open fun detachScreen() {
        this.screen = null
        EventBus.getDefault().unregister(this);
    }
}
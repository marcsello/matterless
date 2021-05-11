package com.marcsello.matterless.ui.login

import com.marcsello.matterless.events.LoginResultEvent
import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) :
    Presenter<LoginScreen>() {

    private var executor = Executors.newFixedThreadPool(1)


    fun performLogin(server: String, username: String, password: String) {

        if (server.isEmpty()) {
            screen?.showLoginError("Server is empty")
            return
        }

        if (username.isEmpty()) {
            screen?.showLoginError("Username is empty")
            return
        }

        if (password.isEmpty()) {
            screen?.showLoginError("Password is empty")
            return
        }

        executor.execute {
            mattermostApiInteractor.performLogin(server, username, password)
        }

    }

    fun tryAutoLogin() {
        executor.execute {
            mattermostApiInteractor.tryAutoLogin()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: LoginResultEvent) {
        if (event.success) {
            screen?.loginSuccessful(event.username)
        } else {
            screen?.showLoginError(event.reason!!)
        }
    }
}
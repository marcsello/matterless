package com.marcsello.matterless.ui.login

import com.marcsello.matterless.ui.Presenter

class LoginPresenter : Presenter<LoginScreen>() {
    fun performLogin(server: String, username: String, password: String) {
        // TODO: Do login
        screen?.loinSuccessful(username);
    }
}
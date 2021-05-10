package com.marcsello.matterless.ui.login

import com.marcsello.matterless.ui.Presenter

class LoginPresenter : Presenter<LoginScreen>() {
    // Ezek azok a fgv-k amiket az activity eventek (button press pl.) hívogatni tud
    // Itt lefutnak a cuccok, vagy igénybe vesznek interaktort
    // És utánna visszahívnak a screen-be
    fun performLogin(server: String, username: String, password: String) {
        // TODO: Do login
        screen?.loinSuccessful(username);
    }
}
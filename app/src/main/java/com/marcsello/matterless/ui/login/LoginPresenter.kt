package com.marcsello.matterless.ui.login

import com.marcsello.matterless.interactor.MattermostApiInteractor
import com.marcsello.matterless.ui.Presenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val mattermostApiInteractor: MattermostApiInteractor) : Presenter<LoginScreen>() {
    // Ezek azok a fgv-k amiket az activity eventek (button press pl.) hívogatni tud
    // Itt lefutnak a cuccok, vagy igénybe vesznek interaktort
    // És utánna visszahívnak a screen-be
    fun performLogin(server: String, username: String, password: String) {
        // TODO: Do login
        screen?.loinSuccessful(username);
    }
}
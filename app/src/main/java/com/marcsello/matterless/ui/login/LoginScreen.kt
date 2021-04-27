package com.marcsello.matterless.ui.login

interface LoginScreen {
    fun showLoginError(reason:String);
    fun loinSuccessful(username:String);
}
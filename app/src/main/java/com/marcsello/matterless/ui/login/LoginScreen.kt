package com.marcsello.matterless.ui.login

interface LoginScreen {
    // Ezek az activity-ben lesznek implementálva
    fun showLoginError(reason:String);
    fun loginSuccessful(username:String, userId:String);
}
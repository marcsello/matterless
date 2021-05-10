package com.marcsello.matterless.ui.login

interface LoginScreen {
    // Ezek az activity-ben lesznek implementálva
    fun showLoginError(reason:String);
    fun loinSuccessful(username:String);
}
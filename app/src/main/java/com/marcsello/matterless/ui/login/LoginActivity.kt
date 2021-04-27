package com.marcsello.matterless.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.marcsello.matterless.R
import com.marcsello.matterless.injector
import com.marcsello.matterless.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginScreen {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        injector.inject(this)

        buttonLogin.setOnClickListener {
            loginPresenter.performLogin(
                editTextServerAddress.text.toString(),
                editTextUserName.text.toString(),
                editTextPassword.text.toString()
            )
        }
    }

    override fun showLoginError(reason: String) {
        Toast.makeText(this, "Login Falied: $reason", Toast.LENGTH_LONG).show();
    }

    override fun loinSuccessful(username: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        loginPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        loginPresenter.detachScreen()
    }

    companion object {
        const val KEY_USERNAME = "KEY_USERNAME"
    }
}
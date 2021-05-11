package com.marcsello.matterless.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcsello.matterless.R
import com.marcsello.matterless.db.AppDatabase
import com.marcsello.matterless.injector
import com.marcsello.matterless.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginScreen {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        injector.inject(this)

        buttonLogin.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                loginPresenter.performLogin(
                    editTextServerAddress.text.toString(),
                    editTextUserName.text.toString(),
                    editTextPassword.text.toString()
                )
            }
        }
    }

    override fun showLoginError(reason: String) {
        Toast.makeText(this, "Login Failed: $reason", Toast.LENGTH_LONG).show();
    }

    // Ez az ami a screen interfészben van definiálva és a presenter hívogatni tudja, ha kész van valamivel
    override fun loginSuccessful(username: String, userId:String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(KEY_USERNAME, username);
        intent.putExtra(KEY_USER_ID, userId);
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        loginPresenter.attachScreen(this)
        lifecycleScope.launch(Dispatchers.IO) {
            loginPresenter.tryAutoLogin()
        }
    }

    override fun onStop() {
        super.onStop()
        loginPresenter.detachScreen()
    }

    companion object {
        const val KEY_USERNAME = "KEY_USERNAME"
        const val KEY_USER_ID = "KEY_USER_ID"
    }
}
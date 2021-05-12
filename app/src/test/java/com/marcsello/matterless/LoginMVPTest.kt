package com.marcsello.matterless

import com.marcsello.matterless.ui.login.LoginPresenter
import com.marcsello.matterless.ui.login.LoginScreen
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test



class LoginMVPTest {

    private val mockLoginScreen = MockLoginScreen()
    // private val loginPresenter = LoginPresenter() // Itt meg kellene valahogy oldani a dependency injectiont... de sajnos ez nem fog összejönni nekem most

    @Before
    fun setUp() {
        mockLoginScreen.reset()
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    class MockLoginScreen:LoginScreen {
        public var showLoginErrorCalled:Int = 0
        public var showLoginSuccessfulCalled:Int = 0

        fun reset() {
            showLoginErrorCalled = 0
            showLoginSuccessfulCalled = 0
        }

        override fun showLoginError(reason: String) {
            showLoginErrorCalled++
        }

        override fun loginSuccessful(username: String, userId: String) {
            showLoginSuccessfulCalled++
        }

    }

}
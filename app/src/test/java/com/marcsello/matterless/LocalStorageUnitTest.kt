package com.marcsello.matterless

import android.app.Application
import android.content.Context
import com.marcsello.matterless.db.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito


class LocalStorageUnitTest {

    fun getTestContext(): Context {
        val application: Application = Mockito.mock(MatterlessApplication::class.java)
        Mockito.`when`(application.applicationContext).thenReturn(application)
        return application
    }

    lateinit var appctx: Context
    lateinit var dbInstance: AppDatabase

    @Before
    fun setUp() {
        appctx = getTestContext()
        dbInstance = AppDatabase.getInstance(appctx)
    }


    @Test
    fun addition_something() {
        runBlocking {
        //    dbInstance.serverDAO().getAllServers() // Itt nem tudom mi történik
        }
    }

}
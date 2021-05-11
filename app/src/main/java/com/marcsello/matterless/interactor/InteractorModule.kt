package com.marcsello.matterless.interactor

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun mattermostApiInteractor(context: Context) = MattermostApiInteractor(context)

    @Provides
    @Singleton
    fun localDataInteractor(context: Context) = LocalDataInteractor(context)
}

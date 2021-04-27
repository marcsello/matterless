package com.marcsello.matterless.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Channel::class, ChannelWithPosts::class, Post::class, Team::class, TeamWithChannels::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun channelDAO(): ChannelDAO
    abstract fun postDAO(): PostDAO
    abstract fun teamDAO(): TeamDAO
    abstract fun userDAO(): UserDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "matterless_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
package com.marcsello.matterless.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "nickname") var nickName: String,
    @ColumnInfo(name = "roles") var roles: String
)
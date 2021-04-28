package com.marcsello.matterless.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// For now, this table will have only a single line
@Entity(tableName = "servers")
data class Server(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "login_id") var loginId: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "session_token") var sessionToken: String?
)
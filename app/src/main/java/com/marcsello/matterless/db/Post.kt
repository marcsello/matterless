package com.marcsello.matterless.db

import androidx.room.*

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "create_at") var createAt: Long,
    @ColumnInfo(name = "user_id") var userId: String,
    @ColumnInfo(name = "channel_id") var channelId: String,
    @ColumnInfo(name = "root_id") var rootId: String?, // Self referencing in Room seems like a bad idea
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "type") var type: String
)

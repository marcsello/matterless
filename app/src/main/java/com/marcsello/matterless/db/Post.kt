package com.marcsello.matterless.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "create_at") var createAt: Long,
    @ColumnInfo(name = "user_id") var userId: String,
    @ColumnInfo(name = "channel_id") var channelId: String,
    @ColumnInfo(name = "root_id") var rootId: String?, // Self referencing in Room seems like a bad idea
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "type") var type: String,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    var user: User
)
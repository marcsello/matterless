package com.marcsello.matterless.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "team_id") var teamId: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "display_name") var displayName: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "last_post_at") var lastPostAt: Long,
    @ColumnInfo(name = "last_read_at") var lastReadAt: Long?
)
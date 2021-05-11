package com.marcsello.matterless.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "display_name") var displayName: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type") var type: String
)
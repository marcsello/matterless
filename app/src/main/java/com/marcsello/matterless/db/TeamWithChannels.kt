package com.marcsello.matterless.db

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithChannels (
    @Embedded val team: Team,
    @Relation (
        parentColumn = "id",
        entityColumn = "team_id"
    )
    val channels: List<Channel>
)
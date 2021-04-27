package com.marcsello.matterless.db

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithChannels (
    @Embedded var team: Team,
    @Relation (
        parentColumn = "id",
        entityColumn = "team_id"
    )
    var channels: List<Channel>
)
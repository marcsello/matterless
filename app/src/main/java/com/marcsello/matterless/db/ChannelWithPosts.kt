package com.marcsello.matterless.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ChannelWithPosts (
    @Embedded var channel: Channel,
    @Relation (
        parentColumn = "id",
        entityColumn = "channel_id"
    )
    var posts: List<Post>
)
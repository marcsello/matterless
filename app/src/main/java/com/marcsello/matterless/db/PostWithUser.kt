package com.marcsello.matterless.db

import androidx.room.Embedded
import androidx.room.Relation

data class PostWithUser(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    val user: User
)
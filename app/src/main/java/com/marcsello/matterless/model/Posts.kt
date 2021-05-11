package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName


data class PostsWrapper(
    @SerializedName("order")
    var order: List<String>? = null,
    @SerializedName("posts")
    var posts: Map<String, Posts>? = null,
    @SerializedName("next_post_id")
    var nextPostId: String? = null,
    @SerializedName("prev_post_id")
    var prevPostId: String? = null
)

data class Posts(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("channel_id")
    var channelId: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("root_id")
    var rootId: String? = null,
    @SerializedName("create_at")
    var createAt: Long? = null,
    @SerializedName("user_id")
    var userId: String? = null,
    @SerializedName("type")
    var type: String? = null
)
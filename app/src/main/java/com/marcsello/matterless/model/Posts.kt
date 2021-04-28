package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName


data class Posts (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("channel_id")
    var channelId: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("root_id")
    var rootId: String? = null,
    @SerializedName("create_at")
    var createAt: Int? = null,
    @SerializedName("user_id")
    var userId: String? = null,
    @SerializedName("type")
    var type: String? = null,
)
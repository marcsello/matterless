package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName

data class NewPost(
    @SerializedName("channel_id")
    var channel_id: String,

    @SerializedName("message")
    var message: String,

    @SerializedName("root_id")
    var root_id: String?
)

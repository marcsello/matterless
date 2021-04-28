package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName

data class Channels (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("team_id")
    var teamId: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("display_name")
    var displayName: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("last_post_at")
    var lastPostat: Int? = null
)
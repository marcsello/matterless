package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName


data class Teams(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("display_name")
    var displayName: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("type")
    var type: String? = null,
)
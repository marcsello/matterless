package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName


data class Users (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("roles")
    var roles: String? = null,
)
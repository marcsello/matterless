package com.marcsello.matterless.model

import com.google.gson.annotations.SerializedName

class ErrorResponse (
    @SerializedName("status_code")
    var statusCode: Int? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("requrest_id")
    var requestId: String? = null
)
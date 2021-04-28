package com.marcsello.matterless.network

import com.marcsello.matterless.model.Teams
import retrofit2.Call
import retrofit2.http.*

interface TeamsApi {
    @POST("users/login")
    fun doLogin(
        @Field("login_id") loginId: String,
        @Field("password") password: String
    ): Call<Teams>

    @GET("users/{user_id}/teams")
    fun getTeamsForUser(
        @Header("Authorization") authorisation: String,
        @Path("user_id") userId: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<Teams>
}
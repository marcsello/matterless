package com.marcsello.matterless.network

import com.marcsello.matterless.model.LoginCredentials
import com.marcsello.matterless.model.Users
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UsersApi {
    @POST("users/login")
    fun doLogin(
        @Body loginCredentials: LoginCredentials
    ): Call<Users>

    @GET("teams/{team_id}/members")
    fun getTeamMembers(
        @Header("Authorization") authorisation: String,
        @Path("team_id") teamId: String
    ): Call<Users>

    @GET("users/{user_id}")
    fun getUserInfo(
        @Header("Authorization") authorisation: String,
        @Path("user_id") userId: String
    ): Call<Users>

    @GET("users/{user_id}/image")
    fun getUserImage(
        @Header("Authorization") authorisation: String,
        @Path("user_id") userId: String
    ): Call<ResponseBody>


}
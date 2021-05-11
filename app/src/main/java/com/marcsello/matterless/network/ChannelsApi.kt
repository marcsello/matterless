package com.marcsello.matterless.network

import com.marcsello.matterless.model.Channels
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelsApi {

    @GET("channels")
    fun getAllChannels(
        @Header("Authorization") authorisation: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<List<Channels>>

    @GET("users/{user_id}/teams/{team_id}/channels")
    fun getChannelsForUser(
        @Header("Authorization") authorisation: String,
        @Path("user_id") userId: String,
        @Path("team_id") teamId: String
    ): Call<List<Channels>>

    @GET("teams/{team_id}/channels")
    fun getChannelsForTeam(
        @Header("Authorization") authorisation: String,
        @Path("team_id") teamId: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<List<Channels>>

}
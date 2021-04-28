package com.marcsello.matterless.network

import com.marcsello.matterless.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsersApi {
    @GET("teams/{team_id}/members")
    fun getTeamMembers(
        @Header("Authorization") authorisation: String,
        @Path("team_id") teamId: String
    ): Call<Users>
}
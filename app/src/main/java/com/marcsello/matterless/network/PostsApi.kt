package com.marcsello.matterless.network

import com.marcsello.matterless.model.Posts
import retrofit2.Call
import retrofit2.http.*

interface PostsApi {

    @POST("posts")
    fun createPost(

        // TODO

    ): Call<Posts>

    @GET("channels/{channel_id}/posts")
    fun getPostsInChannel(
        @Header("Authorization") authorisation: String,
        @Path("channel_id") channelId: String,
        @Query("since") since: Int?,
        @Query("after") after: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<Posts>

    @GET("posts/{post_id}/thread")
    fun getThreadByPost(
        @Header("Authorization") authorisation: String,
        @Path("post_id") postId: String
    ): Call<Posts>
}
package com.marcsello.matterless.network

import com.marcsello.matterless.model.NewPost
import com.marcsello.matterless.model.Posts
import com.marcsello.matterless.model.PostsWrapper
import retrofit2.Call
import retrofit2.http.*

interface PostsApi {

    @POST("posts")
    fun createPost(
        @Header("Authorization") authorisation: String,
        @Body newPost: NewPost
    ): Call<Posts>

    @GET("channels/{channel_id}/posts")
    fun getPostsInChannel(
        @Header("Authorization") authorisation: String,
        @Path("channel_id") channelId: String,
        @Query("since") since: Int?,
        @Query("after") after: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Call<PostsWrapper>

    @GET("posts/{post_id}/thread")
    fun getThreadByPost(
        @Header("Authorization") authorisation: String,
        @Path("post_id") postId: String
    ): Call<PostsWrapper>
}
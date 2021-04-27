package com.marcsello.matterless.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDAO {
    @Query("SELECT * FROM posts ORDER BY create_at ASC")
    suspend fun getPosts(): List<Post>

    @Query("SELECT * FROM posts WHERE channel_id = :channelId ORDER BY create_at ASC")
    suspend fun getPostsOfChannel(channelId: String): List<Post>

    @Query("SELECT * FROM posts WHERE create_at > :since ORDER BY create_at ASC")
    suspend fun getPostsSince(since: Long): List<Post>

    @Query("SELECT * FROM posts WHERE create_at > :since AND channel_id = :channelId ORDER BY create_at ASC")
    suspend fun getPostsOfChannelSince(channelId: String, since: Long): List<Post>

    @Query("SELECT * FROM posts WHERE root_id = :rootId OR id = root_id ORDER BY create_at ASC")
    suspend fun getAllPostsOfThread(rootId: String): List<Post>

    @Insert
    suspend fun insertPosts(vararg post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM teams")
    suspend fun deleteAllPosts()
}
package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface ChannelDAO {
    @Query("SELECT * FROM channels ORDER BY last_read_at DESC")
    suspend fun getChannels(): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId ORDER BY last_read_at DESC")
    suspend fun getChannelsOfTeam(teamId: String): List<Channel>

    @Query("SELECT * FROM channels WHERE last_post_at > :since ORDER BY last_read_at DESC")
    suspend fun getChannelsUpdatedSince(since: Long): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId AND last_post_at > :since ORDER BY last_read_at DESC")
    suspend fun getChannelsOfTeamUpdatedSince(teamId: String, since: Long): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId AND last_post_at > last_read_at ORDER BY last_read_at DESC")
    suspend fun getChannelsOfTeamWithUnread(teamId: String): List<Channel>

    @Insert
    suspend fun insertChannels(vararg channels: Channel)

    @Update
    suspend fun updateChannels(vararg channels: Channel)

    @Delete
    suspend fun deleteChannel(channel: Channel)

    @Query("DELETE FROM channels")
    suspend fun deleteAllChannles()
}
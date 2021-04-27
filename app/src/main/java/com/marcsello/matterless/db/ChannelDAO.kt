package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface ChannelDAO {
    @Query("SELECT * FROM channels")
    suspend fun getChannels(): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId")
    suspend fun getChannelsOfTeam(teamId: String): List<Channel>

    @Query("SELECT * FROM channels WHERE last_post_at > :since")
    suspend fun getChannelsUpdatedSince(since: Long): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId AND last_post_at > :since")
    suspend fun getChannelsOfTeamUpdatedSince(teamId: String, since: Long): List<Channel>

    @Query("SELECT * FROM channels WHERE team_id = :teamId AND last_post_at > last_read_at")
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
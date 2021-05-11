package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface TeamDAO {
    @Query("SELECT * FROM teams")
    suspend fun getTeams(): List<Team>

    @Transaction
    @Query("SELECT * FROM teams")
    suspend fun getTeamsWithChannels(): List<TeamWithChannels>

    @Transaction
    @Query("SELECT * FROM teams WHERE id = :id")
    suspend fun getTeamWithChannelsById(id:String): List<TeamWithChannels>

    @Insert
    suspend fun insertTeams(vararg teams: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()
}
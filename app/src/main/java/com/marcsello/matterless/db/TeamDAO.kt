package com.marcsello.matterless.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamDAO {
    @Query("""SELECT * FROM teams""")
    suspend fun getTeams(): List<Team>

    @Insert
    suspend fun insertTeams(vararg teams: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()
}
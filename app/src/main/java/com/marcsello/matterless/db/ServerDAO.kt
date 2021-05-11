package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface ServerDAO {
    @Query("SELECT * FROM servers")
    suspend fun getAllServers(): List<Server>

    @Query("UPDATE servers SET last_opened_team_id = :teamId")
    suspend fun storeLastTeam(teamId:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServers(vararg servers: Server)

    @Delete
    suspend fun deleteServer(server: Server)

    @Query("DELETE FROM servers")
    suspend fun deleteAllServers()
}
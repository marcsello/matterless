package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface ServerDAO {
    @Query("SELECT * FROM servers")
    suspend fun getAllServers(): List<Server>

    @Insert
    suspend fun insertServers(vararg servers: Server)

    @Delete
    suspend fun deleteServer(server: Server)

    @Query("DELETE FROM servers")
    suspend fun deleteAllServers()
}
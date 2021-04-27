package com.marcsello.matterless.db

import androidx.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id:String): User

    @Query("SELECT * FROM users WHERE first_name LIKE :name_query OR last_name LIKE :name_query OR username LIKE :name_query OR nickname LIKE :name_query")
    suspend fun searchUserByAnyName(name_query:String): List<User>

    @Insert
    suspend fun insertUsers(vararg users: User)

    @Update
    suspend fun updateUsers(vararg users: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
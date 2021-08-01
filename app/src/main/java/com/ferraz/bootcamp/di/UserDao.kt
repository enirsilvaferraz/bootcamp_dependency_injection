package com.ferraz.bootcamp.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE cpf = :cpf")
    suspend fun getById(
        cpf: String
    ): User?

    @Insert(onConflict = REPLACE)
    fun insert(user: User?)
}
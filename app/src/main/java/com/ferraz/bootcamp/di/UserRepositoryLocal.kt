package com.ferraz.bootcamp.di

import android.content.Context
import androidx.room.Room

class UserRepositoryLocal(context: Context) : UserRepository {

    private val database = Room.databaseBuilder(context, AppDataBase::class.java, "my-db").allowMainThreadQueries().build()

    override suspend fun getById(uuid: String) = database.userDao().getById(uuid)

    override suspend fun save(user: User?) = database.userDao().insert(user)
}
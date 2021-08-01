package com.ferraz.bootcamp.di

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = arrayOf(User::class))
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
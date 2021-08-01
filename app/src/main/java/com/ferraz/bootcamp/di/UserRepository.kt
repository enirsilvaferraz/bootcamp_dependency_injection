package com.ferraz.bootcamp.di

interface UserRepository {

    suspend fun getById(uuid: String): User?

    suspend fun save(user: User?)
}
package com.ferraz.bootcamp.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepositoryRemote : UserRepository {

    private val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    override suspend fun getById(uuid: String) =
        retrofit.create(UserWebService::class.java).getById(uuid)

    override suspend fun save(user: User?) {
        TODO("Not yet implemented")
    }
}
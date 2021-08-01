package com.ferraz.bootcamp.di

import retrofit2.http.GET
import retrofit2.http.Path

interface UserWebService {

    @GET("mock/users/user{uuid}.json")
    suspend fun getById(
        @Path("uuid") uuid: String
    ): User
}
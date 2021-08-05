package com.ferraz.bootcamp.di

class UserRepositoryRemote(private val userWebService: UserWebService) : UserRepository {

    override suspend fun getById(uuid: String): User {
        return userWebService.getById(uuid)
    }

    override suspend fun save(user: User?) {
        TODO("Not yet implemented")
    }
}
package com.ferraz.bootcamp.di

class UserRepositoryRemote(private val webService: UserWebService) : UserRepository {

    override suspend fun getById(uuid: String) = webService.getById(uuid)

    override suspend fun save(user: User?) {
        TODO("Not yet implemented")
    }
}
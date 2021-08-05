package com.ferraz.bootcamp.di

class UserRepositoryLocal(private val userDao: UserDao) : UserRepository {

    override suspend fun getById(uuid: String) = userDao.getById(uuid)

    override suspend fun save(user: User?) = userDao.insert(user)
}
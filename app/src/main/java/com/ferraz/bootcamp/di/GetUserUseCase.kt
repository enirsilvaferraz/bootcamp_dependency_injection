package com.ferraz.bootcamp.di

import android.content.Context

class GetUserUseCase(context: Context) {

    val repLocal = UserRepositoryLocal(context)
    val repRemote = UserRepositoryRemote()

    /**
     *
     * Busca o dado no banco
     * - Se encontrar, retorna o dado
     * - Se não encontrar, busca na api
     *
     * Ao buscar o dado na api
     * - Se encontrar, salva no banco e retorna o dado
     * - Se não encontrar, retorna nulo
     *
     */
    suspend fun getUserData(uuid: String): User? {

        return repLocal.getById(uuid) ?: repRemote.getById(uuid).also { user ->
            repLocal.save(user)
        }
    }
}
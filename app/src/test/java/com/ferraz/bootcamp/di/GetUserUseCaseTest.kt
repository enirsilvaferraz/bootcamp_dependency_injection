package com.ferraz.bootcamp.di

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class GetUserUseCaseTest : KoinTest {

    private val testModule = module {

        factory {
            mockk<UserRepositoryLocal>()
        }

        factory {
            mockk<UserRepositoryRemote>()
        }

        factory {
            GetUserUseCase(repLocal = get<UserRepositoryLocal>(), repRemote = get<UserRepositoryRemote>())
        }

        factory {
            User("111.111.111-11", "Teste 1", "Cidade 1", "(99) 99999-9999", "http://teste.com/image1")
        }
    }

    private val getUserUseCase: GetUserUseCase by inject()
    private val user: User by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    @Test
    fun `DADO que nao tenho o cartao salvo QUANDO consulto os cartoes ENTAO devo busca-lo na api`() = runBlocking {

        // DADO
        coEvery { getUserUseCase.repLocal.getById(any()) } returns null
        coEvery { getUserUseCase.repRemote.getById(any()) } returns user
        coEvery { getUserUseCase.repLocal.save(any()) } returns Unit

        // QUANDO
        getUserUseCase.getUserData(user.cpf)

        // ENTAO
        coVerify(exactly = 1) { getUserUseCase.repLocal.getById(any()) }
        coVerify(exactly = 1) { getUserUseCase.repRemote.getById(any()) }
        coVerify(exactly = 1) { getUserUseCase.repLocal.save(any()) }
    }

    @Test
    fun `DADO que tenho o cartao salvo QUANDO consulto por id ENTAO devo busca-lo do banco de dados`() = runBlocking {

        // DADO
        coEvery { getUserUseCase.repLocal.getById(any()) } returns user

        // QUANDO
        getUserUseCase.getUserData(user.cpf)

        // ENTAO
        coVerify(exactly = 1) { getUserUseCase.repLocal.getById(any()) }
        coVerify(exactly = 0) { getUserUseCase.repRemote.getById(any()) }
        coVerify(exactly = 0) { getUserUseCase.repRemote.save(any()) }
    }
}
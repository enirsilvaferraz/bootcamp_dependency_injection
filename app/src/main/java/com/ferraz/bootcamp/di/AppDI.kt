package com.ferraz.bootcamp.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppDI {

    val appModule = module {

        single {
            Room.databaseBuilder(androidContext(), AppDataBase::class.java, "my-db").allowMainThreadQueries().build()
        }

        single {
            get<AppDataBase>().userDao()
        }

        single<Retrofit> {
            Retrofit.Builder().baseUrl(getProperty("server_url")).addConverterFactory(GsonConverterFactory.create()).build()
        }

        single<UserWebService> {
            get<Retrofit>().create(UserWebService::class.java)
        }

        factory(named("LOCAL")) {
            UserRepositoryLocal(userDao = get()) as UserRepository
        }

        factory<UserRepository>(named("REMOTE")) {
            UserRepositoryRemote(webService = get())
        }

        // Cria uma instancia toda vez que precisar
        factory<GetUserUseCase> {
            GetUserUseCase(repLocal = get(named("LOCAL")), repRemote = get(named("REMOTE")))
        }

        // Usado somente para viewModel
        viewModel<CardViewModel> {
            CardViewModel(getUserUseCase = get<GetUserUseCase>())
        }
    }
}
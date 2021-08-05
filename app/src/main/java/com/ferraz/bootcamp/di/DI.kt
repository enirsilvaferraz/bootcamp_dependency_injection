package com.ferraz.bootcamp.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Mapear todas as instancias de classes que precisam de DI
val koinModule = module {

    single { Room.databaseBuilder(androidContext(), AppDataBase::class.java, "my-db").allowMainThreadQueries().build() }

    factory { get<AppDataBase>().userDao() }

    factory { UserRepositoryLocal(userDao = get()) }

    single { Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build() }

    factory { get<Retrofit>().create(UserWebService::class.java) }

    factory { UserRepositoryRemote(userWebService = get()) }

    factory { GetUserUseCase(repLocal = get<UserRepositoryLocal>(), repRemote = get<UserRepositoryRemote>()) }

    viewModel { CardViewModel(getUserUseCase = get()) }
}
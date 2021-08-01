package com.ferraz.bootcamp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.fileProperties

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidLogger()

            androidContext(this@AppApplication)

            fileProperties()

            modules(AppDI.appModule)

            allowOverride(true)
        }
    }
}
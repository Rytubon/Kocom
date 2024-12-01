package com.example.kocom

import android.app.Application
import com.example.kocom.di.databaseModule
import com.example.kocom.di.repositoryModule
import com.example.kocom.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModules,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }
}
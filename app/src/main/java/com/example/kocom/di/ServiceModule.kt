package com.example.kocom.di

import android.content.Context
import com.example.kocom.service.client.local.dao.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabaseService(context = get()) }
}

private fun provideDatabaseService(context: Context): AppDatabase {
    return AppDatabase.getInstance(context)
}


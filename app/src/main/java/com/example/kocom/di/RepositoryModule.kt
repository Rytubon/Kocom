package com.example.kocom.di

import com.example.kocom.service.repositories.UserRepository
import com.example.kocom.service.repositories.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}
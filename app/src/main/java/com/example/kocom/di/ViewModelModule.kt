package com.example.kocom.di

import com.example.kocom.viewmodels.DetailViewModel
import com.example.kocom.viewmodels.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
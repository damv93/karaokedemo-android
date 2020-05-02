package com.example.karaokedemo.presentation.di

import com.example.karaokedemo.data.repository.VideoRepository
import com.example.karaokedemo.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { VideoRepository() }
    viewModel { HomeViewModel(get(), get()) }
}
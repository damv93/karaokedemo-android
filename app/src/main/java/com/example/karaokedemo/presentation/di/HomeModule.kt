package com.example.karaokedemo.presentation.di

import com.example.karaokedemo.data.db.KaraokeDatabase
import com.example.karaokedemo.data.repository.UserRepository
import com.example.karaokedemo.data.repository.VideoRepository
import com.example.karaokedemo.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { UserRepository(KaraokeDatabase.getInstance(androidContext()).userDao) }
    single { VideoRepository() }
    viewModel { HomeViewModel(androidContext(), get(), get()) }
}
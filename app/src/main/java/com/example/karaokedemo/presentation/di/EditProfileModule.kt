package com.example.karaokedemo.presentation.di

import com.example.karaokedemo.presentation.viewmodel.EditProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editProfileModule = module {
    viewModel { EditProfileViewModel(androidContext(), get()) }
}
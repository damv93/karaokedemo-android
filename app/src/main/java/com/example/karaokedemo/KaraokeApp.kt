package com.example.karaokedemo

import android.app.Application
import com.example.karaokedemo.presentation.di.homeModule
import com.example.karaokedemo.presentation.di.editProfileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KaraokeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KaraokeApp)
            modules(homeModule, editProfileModule)
        }
    }

}
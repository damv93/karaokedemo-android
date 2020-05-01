package com.example.karaokedemo

import android.app.Application
import com.example.karaokedemo.presentation.di.homeModule
import org.koin.core.context.startKoin

class KaraokeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(homeModule)
        }
    }

}
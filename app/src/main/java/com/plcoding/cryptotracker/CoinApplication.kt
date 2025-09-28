package com.plcoding.cryptotracker

import android.app.Application
import com.plcoding.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoinApplication)
            androidLogger()
            modules(appModule)
        }
    }
}
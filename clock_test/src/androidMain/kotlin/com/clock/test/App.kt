package com.clock.test

import android.app.Application
import com.clock.test.di.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

// מחלקת האפליקציה שמאתחלת את Koin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(testModule)
        }
    }
} 
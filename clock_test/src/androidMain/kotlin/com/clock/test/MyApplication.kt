package com.clock.test

import android.app.Application
import com.clock.test.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MyApplication)
        }
    }
}
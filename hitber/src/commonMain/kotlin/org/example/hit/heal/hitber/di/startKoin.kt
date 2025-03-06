package org.example.hit.heal.hitber.di

// בקובץ שבו אתה מריץ את Compose
import org.koin.core.context.startKoin

fun startKoinInCommon() {
    startKoin {
        modules(appModule) // אתחול המודולים
    }
}



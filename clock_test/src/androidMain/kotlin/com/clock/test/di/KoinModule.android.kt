package com.clock.test.di

import com.clock.test.Platform
import com.clock.test.presentation.TestViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val targetModule = module {
    single<Platform> { Platform(androidContext()) }
    singleOf(::TestViewModel)
}
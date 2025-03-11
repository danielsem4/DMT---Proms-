package com.clock.test.di

import com.clock.test.presentation.TestViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val testModule = module {
    singleOf(::TestViewModel)
} 
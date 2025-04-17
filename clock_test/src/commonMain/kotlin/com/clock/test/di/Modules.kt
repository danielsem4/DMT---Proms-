package com.clock.test.di

import com.clock.test.presentation.ClockTestViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val CDT_module = module {
    viewModelOf(::ClockTestViewModel)
}
package com.clock.test.di

import com.clock.test.presentation.TestViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule : Module

val sharedModules = module {
    // add repository singleton for viewmodel usage
    viewModelOf(::TestViewModel)
}
package com.clock.test.di

import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration

expect val targetModule:Module

fun createKoinConfiguration(): KoinConfiguration {
    return KoinConfiguration{
        modules(targetModule)
    }
}
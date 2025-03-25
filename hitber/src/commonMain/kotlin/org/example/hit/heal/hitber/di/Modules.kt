package org.example.hit.heal.hitber.di

import org.example.hit.heal.hitber.ActivityViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single  { ActivityViewModel() }
}

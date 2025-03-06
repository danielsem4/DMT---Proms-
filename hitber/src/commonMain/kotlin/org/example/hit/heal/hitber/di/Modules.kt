// קובץ ה-di שלך
package org.example.hit.heal.hitber.di

import org.example.hit.heal.hitber.ActivityViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule: Module = module {
    viewModel { ActivityViewModel() }
}

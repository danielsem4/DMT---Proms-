package org.example.hit.heal.di




import org.example.hit.heal.presentaion.screens.MedicationViewModel.MedicationViewModel


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {//singl

 viewModel { MedicationViewModel( get(), get()) }
}

fun initializeKoin() {
 startKoin {
  modules(appModule)
 }
}
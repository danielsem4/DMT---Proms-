package org.example.hit.heal.di



import MedicationAlarmViewModel
import org.example.hit.heal.presentaion.screens.medicationScreen.MedicationReportViewModel


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {//singl
 viewModel { MedicationAlarmViewModel() }
 viewModel { MedicationReportViewModel() }
}

fun initializeKoin() {
 startKoin {
  modules(appModule)
 }
}
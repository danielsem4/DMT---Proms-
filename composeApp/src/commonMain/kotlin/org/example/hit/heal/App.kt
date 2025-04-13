package org.example.hit.heal

import MedicationAlarmViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.hi.heal.memoryTest.core.presentation.data.presentation.screens.MemoryScreen


import org.example.hit.heal.presentaion.screens.alarmReport.AlarmReportMedicationScreen
import org.example.hit.heal.presentaion.screens.medicationScreen.MedicationReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


val appModule = module {
    viewModel { MedicationAlarmViewModel() }
    viewModel { MedicationReportViewModel() }
}

@Composable
@Preview
fun App() {

    startKoin {
        modules(appModule)
    }
   //Navigator(screen = AlarmReportMedicationScreen()) { navigator ->
   //    SlideTransition(navigator)
   //}

    MaterialTheme {
         Navigator(screen = MemoryScreen()) { navigator ->
             SlideTransition(navigator)
         }
    }
}


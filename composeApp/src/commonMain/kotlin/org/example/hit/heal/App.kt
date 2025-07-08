package org.example.hit.heal

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.hit.heal.splash.SplashScreen
import MedicationAlarmViewModel
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import cafe.adriel.voyager.transitions.SlideTransition
import com.example.new_memory_test.presentation.screens.MemoryScreen.MemoryScreen
import org.example.hit.heal.di.appModule


import org.example.hit.heal.presentaion.screens.alarmReport.AlarmReportMedicationScreen
import org.example.hit.heal.presentaion.screens.medicationScreen.MedicationReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


//val appModule = module {
//    viewModel { MedicationAlarmViewModel() }
//    viewModel { MedicationReportViewModel() }
//    viewModel { ViewModelMemoryTest()}
//}

@Composable
fun App() {

     //startKoin {
        //modules(appModule)
        // }
        //Navigator(screen = AlarmReportMedicationScreen()) { navigator ->
        //    SlideTransition(navigator)
        //}

  //  MaterialTheme {
  //      Navigator(SplashScreen())
  //  }

    //MaterialTheme {
    //    Navigator(SplashScreen()) { navigator ->
    //        SlideTransition(navigator) // This defines the animation for ALL transitions
    //    }
    //}

    MaterialTheme {
        Navigator(screen = MemoryScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
    //MaterialTheme {
    //    Navigator(SplashScreen())
    //}
}


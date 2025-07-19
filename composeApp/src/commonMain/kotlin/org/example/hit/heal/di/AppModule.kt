package org.example.hit.heal.di




import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import org.example.hit.heal.presentaion.screens.MedicationViewModel.MedicationViewModel


import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {//singl


  singleOf(::MedicationViewModel)


}

fun initializeKoin() {
 startKoin {
  modules(appModule)
 }
}
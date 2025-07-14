package com.example.new_memory_test.di


import org.koin.core.module.dsl.viewModel
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MemoryModule = module {
    viewModelOf(::ViewModelMemoryTest)

}
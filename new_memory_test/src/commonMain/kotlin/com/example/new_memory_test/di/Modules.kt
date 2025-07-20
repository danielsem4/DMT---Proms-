package com.example.new_memory_test.di
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module

val MemoryModule = module {
    singleOf(::ViewModelMemoryTest)

}
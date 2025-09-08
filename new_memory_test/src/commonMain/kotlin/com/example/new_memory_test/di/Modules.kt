package com.example.new_memory_test.di
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import com.example.new_memory_test.presentation.screens.RoomScreen.screen.RoomsViewModel
import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module

val MemoryModule = module {
    singleOf(::ViewModelMemoryTest)
    singleOf(::RoomsViewModel)

}
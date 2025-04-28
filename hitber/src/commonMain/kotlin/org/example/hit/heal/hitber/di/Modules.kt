package org.example.hit.heal.hitber.di


import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.core.data.HttpClientFactory
import org.example.hit.heal.hitber.data.CogDataRepository
import org.example.hit.heal.hitber.data.ImageUploadRepository
import org.example.hit.heal.hitber.presentation.CogDataViewModel
import org.example.hit.heal.hitber.presentation.buildShape.TenthQuestionViewModel
import org.example.hit.heal.hitber.presentation.concentration.ThirdQuestionViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.SeventhQuestionViewModel
import org.example.hit.heal.hitber.presentation.naming.FourthQuestionViewModel
import org.example.hit.heal.hitber.presentation.shapes.SecondQuestionViewModel
import org.example.hit.heal.hitber.presentation.timeAndPlace.FirstQuestionViewModel
import org.example.hit.heal.hitber.presentation.understanding.SixthQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val appModule: Module = module {

    single { HttpClientFactory.createHttpClient(get()) }
    single { ImageUploadRepository(get()) }
    //single { ImageUploadViewModel(get()) }
    single { ActivityViewModel() }
    single { CogDataRepository(get()) }
    single { CogDataViewModel(get()) }
    viewModel { FirstQuestionViewModel() }
    single { SecondQuestionViewModel() }
    viewModel { ThirdQuestionViewModel() }
    viewModel { FourthQuestionViewModel() }
    viewModel { SixthQuestionViewModel() }
    viewModel { SeventhQuestionViewModel() }
    viewModel { EightQuestionViewModel() }
    viewModel { TenthQuestionViewModel() }
}

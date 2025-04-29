package org.example.hit.heal.hitber.di


import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.core.data.HttpClientFactory
import org.example.hit.heal.hitber.core.network.AppApi
import org.example.hit.heal.hitber.core.network.KtorAppRemoteDataSource
import org.example.hit.heal.hitber.core.utils.BitmapToUploadUseCase
import org.example.hit.heal.hitber.core.utils.UploadEvaluationUseCase
import org.example.hit.heal.hitber.core.utils.UploadImageUseCase
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
    single { ActivityViewModel(get(), get()) }
    viewModel { FirstQuestionViewModel() }
    single { SecondQuestionViewModel() }
    viewModel { ThirdQuestionViewModel() }
    viewModel { FourthQuestionViewModel() }
    viewModel { SixthQuestionViewModel() }
    viewModel { SeventhQuestionViewModel() }
    viewModel { EightQuestionViewModel() }
    viewModel { TenthQuestionViewModel() }
    single<AppApi> { KtorAppRemoteDataSource(get()) }
    single { UploadImageUseCase(get()) }
    single { UploadEvaluationUseCase(get()) }
}

package di

import core.domain.use_case.PlayAudioUseCase
import core.utils.AudioPlayer
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import presentation.appsDeviceScreen.AppDeviceViewModel
import presentation.appsDeviceScreen.WrongAppViewModel
import presentation.components.CountdownDialogHandler
import presentation.components.CountdownTimerUseCase
import presentation.contatcts.ContactsViewModel
import presentation.detailedContact.DetailedContactViewModel
import presentation.dialScreen.DialScreenViewModel
import presentation.entryScreen.EntryViewModel
import presentation.nextQuestion.NextQuestionViewModel

val Pass_module = module {
    viewModel  { EntryViewModel(get()) }
    viewModel { AppDeviceViewModel(get(), get()) }
    viewModel { WrongAppViewModel(get(), get()) }
    viewModel { ContactsViewModel(get(), get()) }
    viewModel { DetailedContactViewModel(get(), get()) }
    viewModel { NextQuestionViewModel(get()) }
    viewModel { DialScreenViewModel(get(), get()) }
    single { CountdownDialogHandler(get()) }
    single { CountdownTimerUseCase(get()) }
}

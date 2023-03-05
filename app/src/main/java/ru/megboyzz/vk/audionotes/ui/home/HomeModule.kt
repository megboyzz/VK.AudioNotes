package ru.megboyzz.vk.audionotes.ui.home

import ru.megboyzz.vk.audionotes.ui.recordingdetails.RecordingDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
    viewModel { RecordingDetailsViewModel(get()) }

    single { RecordPlayer(androidContext()) }
}
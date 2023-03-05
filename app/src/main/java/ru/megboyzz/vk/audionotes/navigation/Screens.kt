package ru.megboyzz.vk.audionotes.navigation

import androidx.annotation.StringRes
import ru.megboyzz.vk.audionotes.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object RecordingDetails : Screen("recordingDetails", R.string.recordingDetails)
}
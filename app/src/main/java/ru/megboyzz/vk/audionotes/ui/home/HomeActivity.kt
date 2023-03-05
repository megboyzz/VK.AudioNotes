package ru.megboyzz.vk.audionotes.ui.home

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import ru.megboyzz.vk.audionotes.ui.Main
import ru.megboyzz.vk.audionotes.ui.composebase.MainTheme
import ru.megboyzz.vk.audionotes.ui.recordingdetails.RecordingDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
class HomeActivity : AppCompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private val recordingDetailsViewModel by viewModel<RecordingDetailsViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startRecording()
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Surface(color = colors.background) {
                    Main(homeViewModel, recordingDetailsViewModel, this)
                }
            }
        }

        homeViewModel.folderPath = externalCacheDir?.absolutePath
            ?: throw IllegalStateException("externalCacheDir is null! LILLOOO!")
    }

    fun requestAudioRecording() {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    private fun startRecording() {
        homeViewModel.startRecording()
    }

}
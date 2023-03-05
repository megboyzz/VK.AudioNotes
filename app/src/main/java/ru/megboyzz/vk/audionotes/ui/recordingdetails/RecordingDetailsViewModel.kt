package ru.megboyzz.vk.audionotes.ui.recordingdetails

import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.megboyzz.vk.audionotes.ui.home.RecordPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.min

class RecordingDetailsViewModel(private val recordPlayer: RecordPlayer) : ViewModel() {

    val middlePlayer = MutableStateFlow(MiddlePlayer())
    val mHandler = Handler(Looper.getMainLooper())

    fun initMediaPlayer(uri: Uri) {


        viewModelScope.launch {
            recordPlayer.prepareMediaPlayer(uri)
            middlePlayer.emit(
                middlePlayer.value.copy(
                    duration = recordPlayer.mediaPlayer.duration,
                    currentPosition = recordPlayer.mediaPlayer.currentPosition,
                    isPlaying = false
                ))
        }

        recordPlayer.mediaPlayer.setOnCompletionListener {
            viewModelScope.launch {
                middlePlayer.emit(
                    middlePlayer.value.copy(isPlaying = false, currentPosition = recordPlayer.mediaPlayer.duration)
                )
            }
        }
    }

    fun rewindTenSeconds() {
        recordPlayer.mediaPlayer.seekTo(recordPlayer.mediaPlayer.currentPosition - 10000)
        updateCurrentPosition()
    }

    fun forwardTenSeconds() {
        recordPlayer.mediaPlayer.seekTo(
            recordPlayer.mediaPlayer.currentPosition + (min(
                10000,
                recordPlayer.mediaPlayer.duration - recordPlayer.mediaPlayer.currentPosition
            ))
        )
        updateCurrentPosition()
    }

    fun playMedia() {
        viewModelScope.launch {
            recordPlayer.mediaPlayer.start()

            middlePlayer.emit(
                middlePlayer.value.copy(
                    isPlaying = true
                )
            )
        }

        mHandler.postDelayed(object : Runnable {

            override fun run() {
                if (middlePlayer.value.isPlaying) {
                    mHandler.postDelayed(this, 1000)
                    updateCurrentPosition()
                }
            }
        }, 0)
    }

    fun pauseMedia() {
        viewModelScope.launch {
            recordPlayer.mediaPlayer.pause()
            mHandler.removeCallbacksAndMessages(null)
            middlePlayer.emit(
                middlePlayer.value.copy(
                    isPlaying = false
                )
            )
        }

    }

    private fun updateCurrentPosition() {
        viewModelScope.launch {
            middlePlayer.emit(
                middlePlayer.value.copy(
                    currentPosition = recordPlayer.mediaPlayer.currentPosition
                )
            )
        }
    }
}

data class MiddlePlayer(
    val isPlaying: Boolean = false,
    val currentPosition: Int = 0,
    val duration: Int = 10
)
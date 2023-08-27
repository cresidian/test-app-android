package com.android.testapp.presentation.videorecorder.viewmodel

import androidx.lifecycle.viewModelScope
import com.android.testapp.core.base.viewmodel.BaseViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoRecorderViewModel : BaseViewModel<VideoRecorderViewModel.VideoRecorderViewStates>() {


    private var isRecordingStopped = false

    sealed class VideoRecorderViewStates {
        object StartRecording : VideoRecorderViewStates()
        object StopRecording : VideoRecorderViewStates()
        data class UpdateCountdown(val count: String, val isRecording: Boolean) :
            VideoRecorderViewStates()
    }

    private fun startRecording() {
        emitEvent(VideoRecorderViewStates.StartRecording)
        startCountDown(true)
    }

    fun stopRecording() {
        isRecordingStopped = true
        emitEvent(VideoRecorderViewStates.StopRecording)
    }

    fun startCountDown(isRecording: Boolean) {
        viewModelScope.launch {
            val count = if (isRecording) RECORDING_DURATION else COUNT_DOWN_DURATION
            loop@ for (i in count downTo 1) {
                delay(1000)
                emitEvent(
                    VideoRecorderViewStates.UpdateCountdown(
                        if (i == 1) "" else i.toString(),
                        isRecording
                    )
                )
                if (isRecordingStopped) {
                    emitEvent(VideoRecorderViewStates.UpdateCountdown("", isRecording))
                    break@loop
                }
            }
            if (!isRecording) {
                startRecording()
            } else {
                stopRecording()
            }
        }
    }

    companion object {
        private const val COUNT_DOWN_DURATION = 5 //In seconds
        private const val RECORDING_DURATION = 30 //In seconds
    }

}
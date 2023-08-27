package com.android.testapp.presentation.videorecorder.viewmodel

import androidx.lifecycle.viewModelScope
import com.android.testapp.core.base.viewmodel.BaseViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoRecorderViewModel : BaseViewModel<VideoRecorderViewModel.VideoRecorderViewStates>() {


    sealed class VideoRecorderViewStates {
        data class StartCountdown(val duration: Long) : VideoRecorderViewStates()
        object StartRecording : VideoRecorderViewStates()
        object StopRecording : VideoRecorderViewStates()
    }

    init {
        //emitEvent(VideoRecorderViewStates.StartCountdown(COUNT_DOWN_DURATION))
    }

    fun startRecording() {
        viewModelScope.launch {
            emitEvent(VideoRecorderViewStates.StartRecording)
            delay(RECORDING_DURATION)
            stopRecording()
        }
    }

    private fun stopRecording() {
        emitEvent(VideoRecorderViewStates.StopRecording)
    }


    companion object {
        private const val RECORDING_DURATION = 5000L
    }

}
package com.android.testapp.presentation.videorecorder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.android.testapp.app.theme.TestAppTheme
import com.android.testapp.presentation.components.CameraHeadsUpDisplay
import com.android.testapp.presentation.videoplayer.VideoPlayerActivity
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class VideoRecorderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val cameraState = rememberCameraState()
                    val camSelector by rememberCamSelector(CamSelector.Front)
                    Box {
                        CameraPreview(
                            cameraState = cameraState,
                            camSelector=camSelector,
                            captureMode = CaptureMode.Video,
                        )
                        CameraHeadsUpDisplay(this@VideoRecorderActivity, onCountDownFinish = {
                            /*startRecording(cameraState)
                            lifecycleScope.launch {
                                withContext(Dispatchers.Default) {
                                    waitingTime()
                                }
                                stopRecording(cameraState)
                            }*/
                        })
                    }
                }
            }
        }
    }

    private fun startRecording(cameraState: CameraState) {
        cameraState.startRecording(
            file = File.createTempFile(
                System.currentTimeMillis().toString(),
                ".mp4"
            ),
            onResult = { videoCaptureResult: VideoCaptureResult ->
                when (videoCaptureResult) {
                    is VideoCaptureResult.Error -> {
                        Log.d("HERERERER",videoCaptureResult.toString())
                    }
                    is VideoCaptureResult.Success -> {
                        Log.d("HERERERER","OnRecordResult")
                        startActivity(
                            Intent(
                                this@VideoRecorderActivity,
                                VideoPlayerActivity::class.java
                            ).apply {
                                putExtra(PARAM_VIDEO_EXTRA, videoCaptureResult.savedUri.toString())
                            })
                    }
                }
            }
        )
        Log.d("HERERERER","RecordingStarted")
    }

    suspend fun waitingTime(){
        delay(5000)
    }

    private fun stopRecording(cameraState: CameraState) {
        cameraState.stopRecording()
    }

    companion object {
        const val PARAM_VIDEO_EXTRA = "video-uri"
    }
}
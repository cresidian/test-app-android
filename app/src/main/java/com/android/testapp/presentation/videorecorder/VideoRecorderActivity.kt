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
import com.android.testapp.app.theme.TestAppTheme
import com.android.testapp.presentation.components.CameraHeadsUpDisplay
import com.android.testapp.presentation.videoplayer.VideoPlayerActivity
import com.google.android.material.color.utilities.MaterialDynamicColors.onError
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.*
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
                            camSelector = camSelector,
                        ) {

                        }
                        CameraHeadsUpDisplay(this@VideoRecorderActivity, onCountDownFinish = {
                            startRecording(cameraState)
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
                ".mp4",
                externalCacheDir
            ),
            onResult = { videoCaptureResult: VideoCaptureResult ->
                when (videoCaptureResult) {
                    is VideoCaptureResult.Error -> {}
                    is VideoCaptureResult.Success -> {
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
        //cameraState.stopRecording(file) { result -> /* ... */ }
    }

    private fun stopRecording() {

    }

    companion object {
        const val PARAM_VIDEO_EXTRA = "video-uri"
    }
}
package com.android.testapp.presentation.videorecorder

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
import com.android.testapp.presentation.composables.CameraHeadsUpDisplay
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import kotlin.math.log

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
                            // Camera Preview UI
                        }
                        CameraHeadsUpDisplay(this@VideoRecorderActivity, onCountDownFinish = {

                        })
                    }
                }
            }
        }
    }
}
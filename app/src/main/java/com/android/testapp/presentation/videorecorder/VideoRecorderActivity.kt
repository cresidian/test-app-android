package com.android.testapp.presentation.videorecorder

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.video.VideoRecordEvent.Finalize
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.testapp.core.base.activty.BaseActivity
import com.android.testapp.core.extensions.setVisible
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.android.testapp.databinding.ActivityVideoRecorderBinding
import com.android.testapp.presentation.videorecorder.viewmodel.VideoRecorderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.android.testapp.R
import com.android.testapp.presentation.videoplayer.VideoPlayerActivity


@AndroidEntryPoint
class VideoRecorderActivity : BaseActivity() {


    private val viewModel: VideoRecorderViewModel by viewModels()
    private lateinit var bind: ActivityVideoRecorderBinding
    private var service: ExecutorService? = null
    private var recording: Recording? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private val cameraFacing = CameraSelector.LENS_FACING_FRONT
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result: Boolean? ->
        if (ActivityCompat.checkSelfPermission(
                this@VideoRecorderActivity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initializeCamera(cameraFacing)
        }
    }
    private var countDownTimer: CountDownTimer? = null
    private var isRecording = false
    private var lastRecordedVideoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityVideoRecorderBinding.inflate(layoutInflater)
        val view = bind.root
        setContentView(view)
        init()
    }

    private fun init() {
        if (ActivityCompat.checkSelfPermission(
                this@VideoRecorderActivity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activityResultLauncher.launch(Manifest.permission.CAMERA)
        } else {
            initializeCamera(cameraFacing)
        }
        service = Executors.newSingleThreadExecutor()
        bind.btnToggleStopPlay.setOnClickListener {
            if (isRecording) {
                stopRecordingVideo()
            } else if (lastRecordedVideoUri != null) {
                startActivity(Intent(this, VideoPlayerActivity::class.java).apply {
                    putExtra(PARAM_VIDEO_EXTRA, lastRecordedVideoUri.toString())
                })
            }
        }
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewEvents.collect { state ->
                        when (state) {
                            is VideoRecorderViewModel.VideoRecorderViewStates.StartCountdown -> {
                                startCountDownTimer(state.duration)
                            }
                            is VideoRecorderViewModel.VideoRecorderViewStates.StartRecording -> {
                                startRecordingVideo()
                            }
                            is VideoRecorderViewModel.VideoRecorderViewStates.StopRecording -> {
                                stopRecordingVideo()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun startRecordingVideo() {
        val recording1 = recording
        if (recording1 != null) {
            recording1.stop()
            recording = null
            return
        }
        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(
            System.currentTimeMillis()
        )
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
        val options = MediaStoreOutputOptions.Builder(
            contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
            .setContentValues(contentValues).build()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        recording =
            videoCapture!!.output.prepareRecording(this@VideoRecorderActivity, options)
                .withAudioEnabled()
                .start(
                    ContextCompat.getMainExecutor(this@VideoRecorderActivity)
                ) { videoRecordEvent: VideoRecordEvent? ->
                    if (videoRecordEvent is VideoRecordEvent.Start) {
                        isRecording = true
                        bind.btnToggleStopPlay.setVisible(true)
                        toggleVideoRecorderHudVisibility(false)
                    } else if (videoRecordEvent is Finalize) {
                        if (!videoRecordEvent.hasError()) {
                            isRecording = false
                            lastRecordedVideoUri = videoRecordEvent.outputResults.outputUri
                            toggleVideoRecorderHudVisibility(true)
                            showToast(getString(R.string.response_recorded))
                        } else {
                            recording!!.close()
                            recording = null
                            showToast("${getString(R.string.error)}: ${videoRecordEvent.error}")
                        }
                    }
                }
    }

    private fun stopRecordingVideo() {
        bind.btnToggleStopPlay.setImageResource(R.drawable.ic_play)
        recording?.stop()
    }

    private fun initializeCamera(cameraFacing: Int) {
        val processCameraProvider = ProcessCameraProvider.getInstance(this@VideoRecorderActivity)
        processCameraProvider.addListener({
            try {
                val cameraProvider = processCameraProvider.get()
                val preview =
                    Preview.Builder().build()
                preview.setSurfaceProvider(bind.viewFinder.surfaceProvider)
                val recorder =
                    Recorder.Builder()
                        .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                        .build()
                videoCapture = VideoCapture.withOutput(recorder)
                cameraProvider.unbindAll()
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(cameraFacing).build()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoCapture)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this@VideoRecorderActivity))
    }

    private fun startCountDownTimer(duration: Long) {
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                bind.tvCountdown.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                viewModel.startRecording()
            }
        }
        countDownTimer!!.start()
    }

    private fun toggleVideoRecorderHudVisibility(isShow: Boolean) {
        bind.ivFaceFrame.setVisible(isShow)
        bind.tvCountdown.setVisible(isShow)
        bind.tvFeedback.setVisible(isShow)
    }

    override fun onDestroy() {
        super.onDestroy()
        service?.shutdown()
        countDownTimer?.cancel()
    }

    companion object {
        const val PARAM_VIDEO_EXTRA = "PARAM_VIDEO_EXTRA"
    }
}
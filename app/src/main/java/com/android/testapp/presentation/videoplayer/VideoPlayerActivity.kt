package com.android.testapp.presentation.videoplayer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.testapp.databinding.ActivityVideoPlayerBinding
import com.android.testapp.presentation.videorecorder.VideoRecorderActivity.Companion.PARAM_VIDEO_EXTRA
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class VideoPlayerActivity : AppCompatActivity() {


    private lateinit var bind: ActivityVideoPlayerBinding
    var simpleExoPlayer: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityVideoPlayerBinding.inflate(layoutInflater)
        val view = bind.root
        setContentView(view)
        init()

    }

    private fun init() {
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        val videoUri = Uri.parse(intent.getStringExtra(PARAM_VIDEO_EXTRA))
        playVideo(videoUri)
    }

    private fun playVideo(uri: Uri) {
        val mediaItem: MediaItem = MediaItem.fromUri(uri)

        bind.exoPlayerView.player = simpleExoPlayer
        simpleExoPlayer!!.setMediaItem(mediaItem)
        simpleExoPlayer!!.prepare()
    }
}
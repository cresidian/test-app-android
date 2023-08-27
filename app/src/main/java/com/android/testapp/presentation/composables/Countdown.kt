package com.android.testapp.presentation.composables

import android.os.CountDownTimer
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun Countdown(countdownDuration: Long = 5000, onCountDownFinish: () -> Unit) {
    var timeRemaining by remember { mutableStateOf(5L) }
    val countDownTimer = object : CountDownTimer(countdownDuration, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeRemaining = millisUntilFinished / 1000
        }

        override fun onFinish() {
            onCountDownFinish.invoke()
        }
    }
    DisposableEffect(Unit) {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
    Text(
        text = if (timeRemaining == 0L) "" else timeRemaining.toString(),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp
    )
}


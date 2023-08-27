package com.android.testapp.presentation.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.testapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CameraHeadsUpDisplay(context: Context, onCountDownFinish: () -> Unit) {
    var feedbackMessage by remember { mutableStateOf(context.getString(R.string.take_a_deep_breath)) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(id = R.drawable.img_face_frame),
                contentDescription = "",
                modifier = Modifier.size(300.dp, 600.dp)
            )
            Countdown(onCountDownFinish = {
                feedbackMessage = context.getString(R.string.recording)
                onCountDownFinish.invoke()
            })
        }
        Text(text = feedbackMessage, color = Color.White, fontSize = 20.sp)
    }


}
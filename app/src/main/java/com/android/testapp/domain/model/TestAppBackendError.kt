package com.android.testapp.domain.model

import com.google.gson.annotations.SerializedName

data class TestAppBackendError(
    @SerializedName(value="message",alternate = ["detail"])
    val message: String,
    @SerializedName("ErrorCode")
    val errorCode: Int,
    @SerializedName("ErrorMessage")
    val errorMessage: String=""
)
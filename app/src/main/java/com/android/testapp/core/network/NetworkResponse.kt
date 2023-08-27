package com.android.testapp.core.network

import com.android.testapp.domain.model.TestAppBackendError

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val error: TestAppBackendError) : NetworkResponse<Nothing>()
}

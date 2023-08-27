package com.android.testapp.core.network

sealed class NetworkExceptions(error: Throwable) : Exception(error) {
    data class NoNetworkException(val error: Throwable) : NetworkExceptions(error)
    data class ServerUnreachableException(val error: Throwable) : NetworkExceptions(error)
    data class HttpCallFailureException(val error: Throwable) : NetworkExceptions(error)
    data class ServerException(val error: Throwable) : NetworkExceptions(error)
}

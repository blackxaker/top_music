package com.example.vama.utils

import androidx.annotation.Keep

@Keep
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class SuccessWithErrorConnection<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: Throwable? = null) :
        ResultWrapper<Nothing>()

    data class ServerError(val errors: String?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
package com.example.vama.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.ParseException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> ResponseNetwork<T>
): ResultWrapper<ResponseNetwork<T>> {
    return withContext(dispatcher) {
        try {
            val invoke = apiCall.invoke()

            if (invoke.errors == null && invoke.payload == null) {
                ResultWrapper.GenericError(error = ParseException("errors and payload is null", 0))
            }

            ResultWrapper.Success(invoke)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(code, throwable)
                }
                else -> {
                    ResultWrapper.GenericError(null, throwable)
                }
            }
        }
    }
}
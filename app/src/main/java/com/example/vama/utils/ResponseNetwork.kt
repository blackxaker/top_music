package com.example.vama.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseNetwork<T>(
    @SerializedName("feed") var payload: T? = null,
    @SerializedName("errors") var errors: String? = null
)
package com.example.vama.data.api.entity

import com.google.gson.annotations.SerializedName

data class FeedNetwork(
    @SerializedName("results")
    val albums: List<AlbumNetwork>,
    val copyright: String?
)
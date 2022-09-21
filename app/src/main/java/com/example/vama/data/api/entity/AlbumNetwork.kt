package com.example.vama.data.api.entity

import com.google.gson.annotations.SerializedName

data class AlbumNetwork(
    val id: Int?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("artistName")
    val author: String?,
    @SerializedName("releaseDate")
    val released: String?,
    @SerializedName("artworkUrl100")
    val imageURL: String?,
    @SerializedName("url")
    val albumURL: String?,
    val label: String?
)
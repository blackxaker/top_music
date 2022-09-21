package com.example.vama.utils

import com.example.vama.data.api.entity.AlbumNetwork
import com.example.vama.domain.model.Album
import java.util.*

fun AlbumNetwork.toModel(): Album = Album(
    id ?: 1,
    title ?: "",
    author ?: "",
    released ?: "",
    imageURL ?: "",
    albumURL ?: "",
    label ?: ""
)
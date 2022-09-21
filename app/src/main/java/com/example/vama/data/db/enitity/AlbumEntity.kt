package com.example.vama.data.db.enitity

import java.util.*

data class AlbumEntity(
    val id: Int,
    val title: String,
    val author: String,
    val released: Date,
    val copyright: String,
    val imageURL: String,
    val albumURL: String,
    val label: String
)
package com.example.vama.domain.model

data class Album(
    val id: Int,
    val title: String,
    val author: String,
    val released: String,
    val imageURL: String,
    val albumURL: String,
    val label: String
)

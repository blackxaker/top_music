package com.example.vama.domain.model

sealed class Category(name: String, limit: Int) {
    data class TOP(val name: String, val limit: Int) : Category(name, limit)
}

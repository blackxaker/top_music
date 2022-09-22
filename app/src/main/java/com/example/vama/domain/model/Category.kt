package com.example.vama.domain.model

sealed class Category(open val name: String, open val limit: Int) {
    data class TOP(override val name: String, override val limit: Int) : Category(name, limit)
}

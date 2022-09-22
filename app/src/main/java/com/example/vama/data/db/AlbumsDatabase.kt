package com.example.vama.data.db

import com.example.vama.data.db.enitity.AlbumEntity
import com.example.vama.domain.model.Category

interface AlbumsDatabase {
    suspend fun getByCategory(category: Category): List<AlbumEntity>
    suspend fun save(albums: List<AlbumEntity>)
    suspend fun getAll(): List<AlbumEntity>

    fun onClose()
}
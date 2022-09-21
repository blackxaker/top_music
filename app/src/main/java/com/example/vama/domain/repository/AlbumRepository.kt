package com.example.vama.domain.repository

import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.utils.ResultWrapper

interface AlbumRepository {
    suspend fun getAllByCategory(category: Category): ResultWrapper<List<Album>>
}
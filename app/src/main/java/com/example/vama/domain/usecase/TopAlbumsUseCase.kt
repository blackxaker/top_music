package com.example.vama.domain.usecase

import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.domain.repository.AlbumRepository
import com.example.vama.utils.ResultWrapper

class TopAlbumsUseCase(
    private val albumRepository: AlbumRepository
) {
    suspend fun get(): ResultWrapper<List<Album>> {
        return albumRepository.getAllByCategory(Category.TOP("most-played", 100))
    }
}
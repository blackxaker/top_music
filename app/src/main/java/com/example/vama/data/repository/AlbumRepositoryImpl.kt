package com.example.vama.data.repository

import com.example.vama.data.datasource.AlbumNetworkDataSource
import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.domain.repository.AlbumRepository
import com.example.vama.utils.ResultWrapper

class AlbumRepositoryImpl(
    private val albumNetworkDataSource: AlbumNetworkDataSource
) : AlbumRepository {
    override suspend fun getAllByCategory(category: Category): ResultWrapper<List<Album>> {
        return albumNetworkDataSource.fetchAlbumsByCategory(category)
    }
}
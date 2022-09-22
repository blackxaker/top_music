package com.example.vama.data.repository

import com.example.vama.data.datasource.AlbumLocalDataSource
import com.example.vama.data.datasource.AlbumNetworkDataSource
import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.domain.repository.AlbumRepository
import com.example.vama.utils.ResultWrapper
import com.example.vama.utils.hasInternet

class AlbumRepositoryImpl(
    private val albumNetworkDataSource: AlbumNetworkDataSource,
    private val albumLocalDataSource: AlbumLocalDataSource
) : AlbumRepository {
    override suspend fun getAllByCategory(category: Category): ResultWrapper<List<Album>> {
        return if (hasInternet()) {
            val fetchAlbumsByCategory = albumNetworkDataSource.fetchAlbumsByCategory(category)

            if (fetchAlbumsByCategory is ResultWrapper.NetworkError) {
                val albumsByCategory = albumLocalDataSource.getAlbumsByCategory(category)

                if (albumsByCategory is ResultWrapper.Success) {
                    return ResultWrapper.SuccessWithErrorConnection(albumsByCategory.value)
                }
            } else if (fetchAlbumsByCategory is ResultWrapper.Success) {
                albumLocalDataSource.save(category, fetchAlbumsByCategory.value)
            }

            fetchAlbumsByCategory
        } else {
            val albumsByCategory = albumLocalDataSource.getAlbumsByCategory(category)

            if (albumsByCategory is ResultWrapper.Success) {
                return ResultWrapper.SuccessWithErrorConnection(albumsByCategory.value)
            }

            albumsByCategory
        }
    }
}
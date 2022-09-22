package com.example.vama.data.datasource

import com.example.vama.data.db.AlbumsDatabase
import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.utils.ResultWrapper
import com.example.vama.utils.toEntity
import com.example.vama.utils.toModel

class AlbumLocalDataSource(
    private val albumsDatabase: AlbumsDatabase
) {
    suspend fun getAlbumsByCategory(category: Category): ResultWrapper<List<Album>> {
        return ResultWrapper.Success(albumsDatabase
            .getByCategory(category)
            .map {
                it.toModel()
            }
        )
    }

    suspend fun save(category: Category, value: List<Album>) {
        albumsDatabase.save(value
            .map {
                it.toEntity(category)
            }
        )
    }
}
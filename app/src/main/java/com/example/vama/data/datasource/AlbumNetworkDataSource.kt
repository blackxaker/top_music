package com.example.vama.data.datasource

import com.example.vama.data.api.AlbumsAPI
import com.example.vama.data.api.entity.AlbumNetwork
import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import com.example.vama.utils.ResponseNetwork
import com.example.vama.utils.ResultWrapper
import com.example.vama.utils.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class AlbumNetworkDataSource(
    private val albumsAPI: AlbumsAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchAlbumsByCategory(category: Category): ResultWrapper<List<Album>> =
        withContext(ioDispatcher) {
            val albumsByCategory = albumsAPI.getAlbumsByCategory(category)

            if (albumsByCategory is ResultWrapper.Success) {
                val payload = albumsByCategory.value
                    .payload

                if (payload?.albums != null) {
                    val mapPayload = payload.albums
                        .map { it.toModel() }

                    return@withContext ResultWrapper.Success(mapPayload)
                } else {
                   return@withContext ResultWrapper.GenericError(error = IllegalArgumentException("payload or data is null: $payload"))
                }
            }

            albumsByCategory as ResultWrapper<List<Album>>
        }
}
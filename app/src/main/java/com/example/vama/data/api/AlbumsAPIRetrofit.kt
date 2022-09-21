package com.example.vama.data.api

import com.example.vama.data.api.entity.FeedNetwork
import com.example.vama.utils.ResponseNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumsAPIRetrofit {
    @GET("api/v2/{country}/music/albums.json")
    suspend fun getAlbums(@Path("country") country: String): ResponseNetwork<FeedNetwork>

    @GET("api/v2/{country}/music/{category}/{limit}/albums.json")
    suspend fun getAlbumsByCategory(
        @Path("country") country: String,
        @Path("category") category: String,
        @Path("limit") limit: Int
    ): ResponseNetwork<FeedNetwork>
}
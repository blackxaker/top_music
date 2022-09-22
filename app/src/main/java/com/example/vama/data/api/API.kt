package com.example.vama.data.api

import com.example.vama.data.api.entity.FeedNetwork
import com.example.vama.domain.model.Category
import com.example.vama.utils.ResponseNetwork
import com.example.vama.utils.ResultWrapper
import com.example.vama.utils.safeApiCall
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API(
    private val baseUrl: String
) : AlbumsAPI {

    private val client: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val albumAPI: AlbumsAPIRetrofit by lazy {
        client.create(AlbumsAPIRetrofit::class.java)
    }

    override suspend fun getByCategory(category: Category): ResultWrapper<ResponseNetwork<FeedNetwork>> {
        return when (category) {
            is Category.TOP -> {
                safeApiCall(IO){
                    albumAPI.getAlbumsByCategory("us", category.name, category.limit)
                }
            }
        }
    }
}
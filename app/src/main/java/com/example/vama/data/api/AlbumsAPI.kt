package com.example.vama.data.api

import com.example.vama.data.api.entity.FeedNetwork
import com.example.vama.domain.model.Category
import com.example.vama.utils.ResponseNetwork
import com.example.vama.utils.ResultWrapper

interface AlbumsAPI {
    suspend fun getByCategory(category: Category) : ResultWrapper<ResponseNetwork<FeedNetwork>>
}
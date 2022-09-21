package com.example.vama.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vama.App
import com.example.vama.domain.model.Album
import com.example.vama.domain.usecase.TopAlbumsUseCase
import com.example.vama.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var albums: MutableLiveData<List<Album>> = MutableLiveData()

    private val topAlbumsUseCase: TopAlbumsUseCase by lazy {
        TopAlbumsUseCase(App.instants.albumRepository)
    }

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = topAlbumsUseCase.get()){
                is ResultWrapper.GenericError -> {
                    println(result.error)
                }
                ResultWrapper.NetworkError -> println("NetworkError")
                is ResultWrapper.ServerError -> println("ServerError")
                is ResultWrapper.Success -> {
                    albums.postValue(result.value!!)
                }
            }

        }
    }
}
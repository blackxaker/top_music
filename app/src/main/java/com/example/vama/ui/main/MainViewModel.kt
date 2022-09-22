package com.example.vama.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vama.App
import com.example.vama.domain.model.Album
import com.example.vama.domain.usecase.TopAlbumsUseCase
import com.example.vama.ui.BaseViewModel
import com.example.vama.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<StateMain>(StateMain.ShowLoading) {

    private val topAlbumsUseCase: TopAlbumsUseCase by lazy {
        TopAlbumsUseCase(App.instants.albumRepository)
    }

    private val albums: MutableLiveData<List<Album>> = MutableLiveData()

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = topAlbumsUseCase.get()) {
                is ResultWrapper.GenericError -> {
                    println(result.error)

                    notifyViewStates(StateMain.ShowGeneralError, StateMain.HideLoading)
                }
                ResultWrapper.NetworkError -> notifyViewStates(
                    StateMain.ShowConnectionError,
                    StateMain.HideLoading
                )
                is ResultWrapper.ServerError -> notifyViewStates(
                    StateMain.ShowServerError,
                    StateMain.HideLoading
                )
                is ResultWrapper.Success -> {
                    albums.postValue(result.value!!)
                    notifyViewStates(StateMain.ShowAlbums(result.value), StateMain.HideLoading)
                }
                is ResultWrapper.SuccessWithErrorConnection -> {
                    albums.postValue(result.value!!)
                    notifyViewStates(
                        StateMain.ShowAlbums(result.value),
                        StateMain.HideLoading,
                        StateMain.ShowConnectionError
                    )
                }
            }
        }
    }

    fun onClickAlbum(position: Int) {
        albums.value?.let {
            viewModelScope.launch(Dispatchers.Main) {
                notifyViewState(StateMain.ShowAlbumDetails(it[position]))
            }
        }
    }

    fun onRefresh() {
        loadAlbums()
    }
}
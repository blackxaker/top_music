package com.example.vama.ui.main

import com.example.vama.domain.model.Album

sealed class StateMain {
    object ShowLoading : StateMain()
    object HideLoading : StateMain()
    object ShowServerError : StateMain()
    object ShowConnectionError : StateMain()
    object ShowGeneralError : StateMain()
    object Wait : StateMain()
    data class ShowAlbums(val albums: List<Album>) : StateMain()
    data class ShowAlbumDetails(val album: Album) : StateMain()
}

package com.example.vama.utils

import com.example.vama.data.api.entity.AlbumNetwork
import com.example.vama.data.db.enitity.AlbumEntity
import com.example.vama.domain.model.Album
import com.example.vama.domain.model.Category
import java.util.*

fun AlbumNetwork.toModel(): Album = Album(
    id ?: 1,
    title ?: "",
    author ?: "",
    released ?: "",
    imageURL ?: "",
    albumURL ?: "",
    label ?: ""
)

fun AlbumEntity.toModel(): Album = Album(
    id,
    title,
    author,
    released,
    imageURL,
    albumURL,
    label
)

fun AlbumEntity.applyChange(albumEntity: AlbumEntity) {
    this.category = albumEntity.category
    this.title = albumEntity.title
    this.author = albumEntity.author
    this.released = albumEntity.released
    this.imageURL = albumEntity.imageURL
    this.albumURL = albumEntity.albumURL
    this.label = albumEntity.label
}

fun Album.toEntity(category: Category): AlbumEntity = AlbumEntity()
    .apply {
        this.category = category.name
        this.id = this@toEntity.id
        this.title = this@toEntity.title
        this.author = this@toEntity.author
        this.released = this@toEntity.released
        this.imageURL = this@toEntity.imageURL
        this.albumURL = this@toEntity.albumURL
        this.label = this@toEntity.label
    }
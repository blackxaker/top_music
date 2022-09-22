package com.example.vama.data.db.enitity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AlbumEntity : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var author: String = ""
    var released: String = ""
    var category: String = ""
    var imageURL: String = ""
    var albumURL: String = ""
    var label: String = ""
}
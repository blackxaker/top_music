package com.example.vama.data.db

import com.example.vama.data.db.enitity.AlbumEntity
import com.example.vama.domain.model.Category
import com.example.vama.utils.applyChange
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find

class AlbumsDatabaseImpl : AlbumsDatabase {

    private val realm: Realm by lazy {
        Realm.open(
            RealmConfiguration.Builder(schema = setOf(AlbumEntity::class))
                .build()
        )
    }

    override suspend fun getByCategory(category: Category): List<AlbumEntity> {
        return realm.query<AlbumEntity>("category = $0", category.name)
            .find()
            .toList()
    }

    override suspend fun save(albums: List<AlbumEntity>) {
        realm.write {
            albums.forEach {
                realm.query<AlbumEntity>("id == $0", it.id)
                    .first()
                    .find { entityFromDB ->
                        if (entityFromDB != null) {
                            entityFromDB.applyChange(it)
                        } else {
                            this.copyToRealm(it)
                        }
                    }
            }
        }
    }

    override suspend fun getAll(): List<AlbumEntity> {
        return realm.query<AlbumEntity>()
            .find()
            .toList()
    }

    override fun onClose() {
        realm.close()
    }
}
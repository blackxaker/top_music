package com.example.vama

import android.app.Application
import com.example.vama.data.api.API
import com.example.vama.data.datasource.AlbumLocalDataSource
import com.example.vama.data.datasource.AlbumNetworkDataSource
import com.example.vama.data.db.AlbumsDatabaseImpl
import com.example.vama.data.repository.AlbumRepositoryImpl
import com.example.vama.domain.repository.AlbumRepository
import io.realm.kotlin.Realm
import kotlinx.coroutines.Dispatchers

class App : Application() {

    private val database = AlbumsDatabaseImpl()
    val albumRepository: AlbumRepository by lazy {
        val api = API("https://rss.applemarketingtools.com/")

        val albumNetworkDataSource = AlbumNetworkDataSource(api, Dispatchers.IO)
        val albumLocalDataSource = AlbumLocalDataSource(database)
        AlbumRepositoryImpl(albumNetworkDataSource, albumLocalDataSource)
    }

    companion object {
        lateinit var instants: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instants = this
    }

    override fun onTerminate() {
        super.onTerminate()

        database.onClose()
    }
}
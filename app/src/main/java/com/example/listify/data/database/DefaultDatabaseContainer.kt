package com.example.listify.data.database

import android.content.Context
import com.example.listify.data.database.album.AlbumsRepository
import com.example.listify.data.database.album.OfflineAlbumsRepository
import com.example.listify.data.database.artist.ArtistsRepository
import com.example.listify.data.database.artist.OfflineArtistsRepository

interface DatabaseContainer {
    val albumsRepository: AlbumsRepository
    val artistRepository: ArtistsRepository
    val artistAlbumsCrossRefRepository: ArtistAlbumCrossRefRepository
}

class DefaultDatabaseContainer(private val context: Context) : DatabaseContainer {

    override val albumsRepository: AlbumsRepository by lazy {
        OfflineAlbumsRepository(ListifyDatabase.getDatabase(context).albumDao())
    }

    override val artistRepository: ArtistsRepository by lazy {
        OfflineArtistsRepository(ListifyDatabase.getDatabase(context).artistDao())
    }

    override val artistAlbumsCrossRefRepository: ArtistAlbumCrossRefRepository by lazy {
        OfflineArtistAlbumCrossRefRepository(ListifyDatabase.getDatabase(context).artistAlbumCrossRefDao())
    }
}
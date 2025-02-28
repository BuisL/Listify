package com.example.listify.data.local

import android.content.Context
import com.example.listify.data.repository.AlbumsRepository
import com.example.listify.data.repository.OfflineAlbumsRepository
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.data.repository.OfflineArtistsRepository
import com.example.listify.data.repository.ArtistAlbumCrossRefRepository
import com.example.listify.data.repository.ArtistGenreCrossRefRepository
import com.example.listify.data.repository.OfflineArtistAlbumCrossRefRepository
import com.example.listify.data.repository.OfflineArtistGenreCrossRefRepository

interface DatabaseContainer {
    val albumsRepository: AlbumsRepository
    val artistRepository: ArtistsRepository
    val artistAlbumsCrossRefRepository: ArtistAlbumCrossRefRepository
    val artistGenreCrossRefRepository: ArtistGenreCrossRefRepository
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

    override val artistGenreCrossRefRepository: ArtistGenreCrossRefRepository by lazy {
        OfflineArtistGenreCrossRefRepository(ListifyDatabase.getDatabase(context).artistGenreCrossRefDao())
    }
}
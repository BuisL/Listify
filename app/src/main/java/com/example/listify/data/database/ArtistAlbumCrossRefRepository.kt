package com.example.listify.data.database

import com.example.listify.data.database.album.AlbumEntity
import com.example.listify.data.database.artist.ArtistEntity

interface ArtistAlbumCrossRefRepository {

    suspend fun insert(crossRef: ArtistAlbumCrossRef)

    suspend fun delete(crossRef: ArtistAlbumCrossRef)
}
package com.example.listify.data.repository

import com.example.listify.data.model.ArtistAlbumCrossRefEntity

interface ArtistAlbumCrossRefRepository {

    suspend fun insert(crossRef: ArtistAlbumCrossRefEntity)

    suspend fun delete(crossRef: ArtistAlbumCrossRefEntity)
}
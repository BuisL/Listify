package com.example.listify.data.repository

import com.example.listify.data.model.ArtistGenreCrossRefEntity

interface ArtistGenreCrossRefRepository {

    suspend fun insert(crossRef: ArtistGenreCrossRefEntity)

    suspend fun delete(crossRef: ArtistGenreCrossRefEntity)
}
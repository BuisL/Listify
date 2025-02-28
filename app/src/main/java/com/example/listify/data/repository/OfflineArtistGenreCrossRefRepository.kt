package com.example.listify.data.repository

import com.example.listify.data.local.ArtistGenreCrossDao
import com.example.listify.data.model.ArtistGenreCrossRefEntity

class OfflineArtistGenreCrossRefRepository(private val joinDao: ArtistGenreCrossDao): ArtistGenreCrossRefRepository {
    override suspend fun insert(crossRef: ArtistGenreCrossRefEntity) = joinDao.insert(crossRef)

    override suspend fun delete(crossRef: ArtistGenreCrossRefEntity) = joinDao.delete(crossRef)
}
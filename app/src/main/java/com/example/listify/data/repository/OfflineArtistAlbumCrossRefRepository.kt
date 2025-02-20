package com.example.listify.data.repository

import com.example.listify.data.local.ArtistAlbumCrossDao
import com.example.listify.data.model.ArtistAlbumCrossRefEntity

class OfflineArtistAlbumCrossRefRepository(private val joinDao: ArtistAlbumCrossDao) :
    ArtistAlbumCrossRefRepository {
    override suspend fun insert(crossRef: ArtistAlbumCrossRefEntity) = joinDao.insert(crossRef)

    override suspend fun delete(crossRef: ArtistAlbumCrossRefEntity) = joinDao.delete(crossRef)

}
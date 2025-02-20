package com.example.listify.data.database

class OfflineArtistAlbumCrossRefRepository(private val joinDao: ArtistAlbumCrossDao) : ArtistAlbumCrossRefRepository{
    override suspend fun insert(crossRef: ArtistAlbumCrossRef) = joinDao.insert(crossRef)

    override suspend fun delete(crossRef: ArtistAlbumCrossRef) = joinDao.delete(crossRef)

}
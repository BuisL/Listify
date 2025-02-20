package com.example.listify.data.repository

import com.example.listify.data.local.AlbumDao
import com.example.listify.data.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

class OfflineAlbumsRepository(private val albumDao: AlbumDao) : AlbumsRepository {

    override fun getAll(): Flow<List<AlbumEntity>> = albumDao.getAll()

    override fun getAlbumStream(id: String): Flow<AlbumEntity?> = albumDao.getAlbum(id)

    override suspend fun insertAlbum(album: AlbumEntity) = albumDao.insert(album)

    override suspend fun deleteAlbum(album: AlbumEntity) = albumDao.delete(album)

    override suspend fun updateAlbum(album: AlbumEntity) = albumDao.update(album)

}
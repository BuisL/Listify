package com.example.listify.data.repository

import com.example.listify.data.model.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    suspend fun insertAlbum(album: AlbumEntity)

    suspend fun deleteAlbum(album: AlbumEntity)

    suspend fun updateAlbum(album: AlbumEntity)

    fun getAll(): Flow<List<AlbumEntity>>

    fun getAlbumStream(id: String): Flow<AlbumEntity?>
}
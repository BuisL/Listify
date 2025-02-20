package com.example.listify.data.database.album

import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbumStream(id: String): Flow<AlbumEntity?>

    suspend fun insertAlbum(album: AlbumEntity)

    suspend fun deleteAlbum(album: AlbumEntity)

    suspend fun updateAlbum(album: AlbumEntity)

    fun getAll(): Flow<List<AlbumEntity>>
}
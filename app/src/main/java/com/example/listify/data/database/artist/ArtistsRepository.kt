package com.example.listify.data.database.artist

import com.example.listify.data.database.album.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface ArtistsRepository {
    fun getArtistStream(id: String): Flow<ArtistEntity?>

    suspend fun insertArtist(artist: ArtistEntity)

    suspend fun deleteArtist(artist: ArtistEntity)

    suspend fun updateArtist(artist: ArtistEntity)

    fun getAll(): Flow<List<ArtistEntity>>

    fun getDiscography(artistId: String): Flow<List<AlbumEntity>>
}
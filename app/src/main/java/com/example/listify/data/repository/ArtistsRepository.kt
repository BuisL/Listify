package com.example.listify.data.repository

import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi
import kotlinx.coroutines.flow.Flow

interface ArtistsRepository {

    suspend fun insertArtist(artist: ArtistEntity)

    suspend fun deleteArtist(artist: ArtistEntity)

    suspend fun updateArtist(artist: ArtistEntity)

    fun getAll(): Flow<List<ArtistEntity>>

    fun getDiscography(artistId: String): Flow<List<AlbumEntity>>

    fun getArtistStream(id: String): Flow<ArtistEntity?>

    fun getDiscographyUi(artistId: String): Flow<List<AlbumUi>>

    fun getArtistUi(artistId: String): Flow<ArtistUi?>
}
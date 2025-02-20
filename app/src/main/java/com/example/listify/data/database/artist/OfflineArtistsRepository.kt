package com.example.listify.data.database.artist

import com.example.listify.data.database.album.AlbumEntity
import kotlinx.coroutines.flow.Flow

class OfflineArtistsRepository(private val artistDao: ArtistDao) : ArtistsRepository {

    override fun getAll(): Flow<List<ArtistEntity>> = artistDao.getAll()

    override fun getArtistStream(id: String): Flow<ArtistEntity?> = artistDao.getArtist(id)

    override suspend fun insertArtist(artist: ArtistEntity) = artistDao.insert(artist)

    override suspend fun deleteArtist(artist: ArtistEntity) = artistDao.delete(artist)

    override suspend fun updateArtist(artist: ArtistEntity) = artistDao.update(artist)

    override fun getDiscography(artistId: String): Flow<List<AlbumEntity>> = artistDao.getAlbumsForArtist(artistId)

}
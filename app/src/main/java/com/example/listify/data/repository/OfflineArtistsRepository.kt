package com.example.listify.data.repository

import com.example.listify.data.converters.toAlbumUi
import com.example.listify.data.converters.toArtistUi
import com.example.listify.data.local.ArtistDao
import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineArtistsRepository(private val artistDao: ArtistDao) : ArtistsRepository {

    override fun getAll(): Flow<List<ArtistEntity>> = artistDao.getAll()

    override fun getArtistStream(id: String): Flow<ArtistEntity?> = artistDao.getArtist(id)

    override suspend fun insertArtist(artist: ArtistEntity) = artistDao.insert(artist)

    override suspend fun deleteArtist(artist: ArtistEntity) = artistDao.delete(artist)

    override suspend fun updateArtist(artist: ArtistEntity) = artistDao.update(artist)

    override fun getDiscography(artistId: String): Flow<List<AlbumEntity>> = artistDao.getAlbumsForArtist(artistId)

    override fun getDiscographyUi(artistId: String): Flow<List<AlbumUi>> {
        return getDiscography(artistId).map { albumEntities -> albumEntities.map { albumEntity -> albumEntity.toAlbumUi() } }
    }

    override fun getArtistUi(artistId: String): Flow<ArtistUi?> {
        return getArtistStream(artistId).map { artist -> artist?.toArtistUi() }
    }

}
package com.example.listify.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: ArtistEntity)

    @Update
    suspend fun update(artist: ArtistEntity)

    @Delete
    suspend fun delete(artist: ArtistEntity)

    @Query("SELECT * from artist WHERE id = :id")
    fun getArtist(id: String): Flow<ArtistEntity>

    @Query("SELECT * from artist")
    fun getAll(): Flow<List<ArtistEntity>>

    @Transaction
    @Query("""
        SELECT * FROM album 
        INNER JOIN artist_album_cross_ref ON album.id = artist_album_cross_ref.album_id
        WHERE artist_album_cross_ref.artist_id = :artistId
    """)
    fun getAlbumsForArtist(artistId: String): Flow<List<AlbumEntity>>
}
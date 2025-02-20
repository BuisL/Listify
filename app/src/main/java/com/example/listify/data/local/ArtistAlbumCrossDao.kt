package com.example.listify.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.listify.data.model.ArtistAlbumCrossRefEntity

@Dao
interface ArtistAlbumCrossDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: ArtistAlbumCrossRefEntity)

    @Delete
    suspend fun delete(crossRef: ArtistAlbumCrossRefEntity)
}
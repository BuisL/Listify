package com.example.listify.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ArtistAlbumCrossDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: ArtistAlbumCrossRef)

    @Delete
    suspend fun delete(crossRef: ArtistAlbumCrossRef)
}
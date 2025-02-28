package com.example.listify.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.listify.data.model.ArtistGenreCrossRefEntity

@Dao
interface ArtistGenreCrossDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: ArtistGenreCrossRefEntity)

    @Delete
    suspend fun delete(crossRef: ArtistGenreCrossRefEntity)
}
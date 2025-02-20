package com.example.listify.data.database.album

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: AlbumEntity)

    @Update
    suspend fun update(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)

    @Query("SELECT * from album WHERE id = :id")
    fun getAlbum(id: String): Flow<AlbumEntity>

    @Query("SELECT * from album")
    fun getAll(): Flow<List<AlbumEntity>>
}
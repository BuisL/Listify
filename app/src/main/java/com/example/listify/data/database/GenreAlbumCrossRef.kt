package com.example.listify.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["album_id", "genre_name"], tableName = "album_genre_cross_ref")
data class GenreAlbumCrossRef(
    @ColumnInfo(name = "album_id") val albumId: String,
    @ColumnInfo(name = "genre_name") val genreName: String,
)

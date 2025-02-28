package com.example.listify.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["artist_id", "genre_name"], tableName = "artist_genre_cross_ref")
data class ArtistGenreCrossRefEntity(
    @ColumnInfo(name = "artist_id") val artistId: String,
    @ColumnInfo(name = "genre_name") val genreName: String,
)

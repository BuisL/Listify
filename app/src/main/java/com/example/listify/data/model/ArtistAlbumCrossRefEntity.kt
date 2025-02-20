package com.example.listify.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["artist_id", "album_id"], tableName = "artist_album_cross_ref")
data class ArtistAlbumCrossRefEntity(
    @ColumnInfo(name = "artist_id") val artistId: String,
    @ColumnInfo(name = "album_id") val albumId: String,
)

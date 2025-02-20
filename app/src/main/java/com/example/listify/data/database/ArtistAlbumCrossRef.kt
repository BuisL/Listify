package com.example.listify.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["artist_id", "album_id"], tableName = "artist_album_cross_ref")
data class ArtistAlbumCrossRef(
    @ColumnInfo(name = "artist_id") val artistId: String,
    @ColumnInfo(name = "album_id") val albumId: String,
)

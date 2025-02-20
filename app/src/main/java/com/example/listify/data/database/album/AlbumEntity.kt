package com.example.listify.data.database.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "album_name") val albumName: String,
    @ColumnInfo(name = "release_year") val releaseYear: String,
    @ColumnInfo(name = "image_url") val imgUrl: String,
    @ColumnInfo(name = "album_group") val albumGroup: String,
    @ColumnInfo(name = "spotify_url") val spotifyUrl: String,
    @ColumnInfo(name = "has_listened") val hasListened: Boolean,
    @ColumnInfo(name = "last_update") val lastUpdate: Long,
    @ColumnInfo(name = "is_listen_later") val isListenLater: Boolean,
)

package com.example.listify.ui.model

data class AlbumUi(
    val id: String,
    val albumName: String,
    val releaseYear: String,
    val imgUrl: String,
    val albumGroup: String,
    val spotifyUrl: String,
    val hasListened: Boolean,
    val lastUpdate: Long,
    val isListenLater: Boolean,
)

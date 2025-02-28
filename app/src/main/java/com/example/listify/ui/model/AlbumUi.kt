package com.example.listify.ui.model

data class AlbumUi(
    val id: String = "",
    val albumName: String = "",
    val releaseYear: String = "",
    val imgUrl: String = "",
    val albumGroup: String = "",
    val spotifyUrl: String = "",
    val hasListened: Boolean = false,
    val lastUpdate: Long = 0,
    val isListenLater: Boolean = false,
    val isFavorite: Boolean = false,
    val rating: Int = 0,
    val note: String = "",
)

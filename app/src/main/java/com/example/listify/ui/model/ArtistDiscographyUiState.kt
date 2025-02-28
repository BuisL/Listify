package com.example.listify.ui.model

data class ArtistDiscographyUiState(
    val allAlbums: List<AlbumUi> = emptyList(),
    val artistName: String = "",
    val listenedAlbums: List<AlbumUi> = emptyList(),
    val selectedAlbum: AlbumUi? = null
)

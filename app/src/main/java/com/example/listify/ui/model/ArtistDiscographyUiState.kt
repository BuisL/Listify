package com.example.listify.ui.model

data class ArtistDiscographyUiState(
    val allAlbums: List<AlbumUi> = emptyList(),
    val artistName: String = "",
    val selectedAlbums: List<AlbumUi> = emptyList()
)

package com.example.listify.ui.model

data class AddArtistUiState(
    val searchResults: List<ArtistUi> = emptyList(),
    val query: String = "",
    val selectedArtist: ArtistUi? = null,
    val allAlbums: List<AlbumUi> = emptyList(),
    val selectedAlbums: List<AlbumUi> = emptyList()
)
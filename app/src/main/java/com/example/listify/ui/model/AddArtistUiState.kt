package com.example.listify.ui.model

data class AddArtistUiState(
    val searchResults: List<ArtistUi> = emptyList(),
    val query: String = "",
    val allAlbums: List<AlbumUi> = emptyList(),
    val selectedAlbums: List<AlbumUi> = emptyList(),
    val albumArtistMap: Map<AlbumUi, List<ArtistUi>> = emptyMap(),
    val isLoading: Boolean = false
)
package com.example.listify.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistAlbumsResponse(
    val href: String = "",
    val items: List<AlbumDto> = emptyList(),
    val next: String? = null,
)
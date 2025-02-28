package com.example.listify.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistListDto(
    val items: List<ArtistDto> = emptyList(),
    val artists: List<ArtistDto> = emptyList()
)

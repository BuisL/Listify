package com.example.listify.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchArtistDto(
    val artists: ArtistListDto
)



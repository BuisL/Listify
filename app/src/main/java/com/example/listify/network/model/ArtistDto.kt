package com.example.listify.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    val id: String = "",
    val images: List<ImageDto> = emptyList(),
    val name: String = "",
)

package com.example.listify.data.database.api

import kotlinx.serialization.Serializable

@Serializable
data class SearchArtistResponse(
    val artists: ArtistList
)

@Serializable
data class ArtistList(
    val items: List<AAArtist>
)

@Serializable
data class AAArtist(
    val id: String = "",
    val images: List<Image> = emptyList(),
    val name: String = "",
)

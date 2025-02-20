package com.example.listify.data.database.api

import com.example.listify.data.Album
import kotlinx.serialization.Serializable

@Serializable
data class ArtistAlbumsResponse(
    val href: String = "",
    val items: List<Album> = emptyList(),
    val next: String? = null,
)


@Serializable
data class Image(
    val url: String = ""
)
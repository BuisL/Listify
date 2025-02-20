package com.example.listify.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    val name: String = "",
    @SerialName(value = "release_date")
    val releaseDate: String = "",
    val images: List<ImageDto> = emptyList(),
    val artists: List<ArtistDto> = emptyList(),
    val external_urls: ExternalUrls? = null,
    val id: String = "",
    val album_group: String = "",
    val hasListened: Boolean = false,
    val lastUpdate: Long = 0,
    val isListenLater: Boolean = false
)

@Serializable
data class ExternalUrls(
    val spotify: String = ""
)

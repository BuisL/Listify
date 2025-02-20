package com.example.listify.data

import com.example.listify.data.database.api.AAArtist
import com.example.listify.data.database.api.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val name: String = "",
    @SerialName(value = "release_date")
    val releaseDate: String = "",
    val images: List<Image> = emptyList(),
    val artists: List<AAArtist> = emptyList(),
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

package com.example.listify.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val url: String = ""
)
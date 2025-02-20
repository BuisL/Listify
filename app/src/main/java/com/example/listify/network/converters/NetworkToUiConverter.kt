package com.example.listify.network.converters

import com.example.listify.network.model.AlbumDto
import com.example.listify.network.model.ArtistDto
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi

fun AlbumDto.toAlbumUi(): AlbumUi = AlbumUi(
    id = id,
    albumName = name,
    releaseYear = releaseDate,
    imgUrl = images[0].url,
    albumGroup = album_group,
    spotifyUrl = external_urls!!.spotify,
    hasListened = hasListened,
    lastUpdate = System.currentTimeMillis(),
    isListenLater = isListenLater
)

fun ArtistDto.toArtistUi(): ArtistUi {

    val imageUrlTemp = if (images.isEmpty()) "" else images[0].url

    return ArtistUi(
        id = id,
        artistName = name,
        imageUrl = imageUrlTemp,
        completionPercent = 0f,
    )
}
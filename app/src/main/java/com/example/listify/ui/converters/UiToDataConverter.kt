package com.example.listify.ui.converters

import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi

fun ArtistUi.toArtistEntity(): ArtistEntity = ArtistEntity(
    id = id,
    artistName = artistName,
    imageUrl = imageUrl,
    completionPercent = 0f,
    isFavorite = isFavorite,
    rating = rating,
    note = note
)

fun AlbumUi.toAlbumEntity(): AlbumEntity = AlbumEntity(
    id = id,
    albumName = albumName,
    releaseYear = releaseYear,
    imgUrl = imgUrl,
    albumGroup = albumGroup,
    spotifyUrl = spotifyUrl,
    hasListened = hasListened,
    lastUpdate = lastUpdate,
    isListenLater = isListenLater,
    isFavorite = isFavorite,
    note = note,
    rating = rating
)


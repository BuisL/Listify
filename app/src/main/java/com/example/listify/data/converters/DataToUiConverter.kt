package com.example.listify.data.converters

import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi

fun AlbumEntity.toAlbumUi(): AlbumUi = AlbumUi(
    id = id,
    albumName = albumName,
    releaseYear = releaseYear,
    imgUrl = imgUrl,
    albumGroup = albumGroup,
    spotifyUrl = spotifyUrl,
    hasListened = hasListened,
    lastUpdate = lastUpdate,
    isListenLater = isListenLater,
    rating = rating,
    isFavorite = isFavorite,
    note = note
)

fun ArtistEntity.toArtistUi(): ArtistUi = ArtistUi(
    id = id,
    artistName = artistName,
    imageUrl = imageUrl,
    completionPercent = completionPercent,
    note = note,
    rating = rating,
    isFavorite = isFavorite
)
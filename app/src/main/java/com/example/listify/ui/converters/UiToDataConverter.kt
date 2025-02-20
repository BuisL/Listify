package com.example.listify.ui.converters

import com.example.listify.data.model.AlbumEntity
import com.example.listify.data.model.ArtistEntity
import com.example.listify.ui.model.AlbumUi
import com.example.listify.ui.model.ArtistUi
import com.example.listify.ui.screens.addArtist.AddArtist

fun AddArtist.toAlbumEntity(): AlbumEntity = AlbumEntity(
    id = album.id,
    albumName = album.albumName,
    releaseYear = album.releaseYear,
    imgUrl = album.imgUrl,
    albumGroup = album.albumGroup,
    spotifyUrl = album.spotifyUrl,
    hasListened = false,
    lastUpdate = System.currentTimeMillis(),
    isListenLater = false
)

fun ArtistUi.toArtistEntity(): ArtistEntity = ArtistEntity(
    id = id,
    artistName = artistName,
    imageUrl = imageUrl,
    completionPercent = 0f
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
    isListenLater = isListenLater
)


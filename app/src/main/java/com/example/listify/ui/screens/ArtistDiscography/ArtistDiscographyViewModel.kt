package com.example.listify.ui.screens.ArtistDiscography

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listify.ListifyApplication
import com.example.listify.data.Album
import com.example.listify.data.ExternalUrls
import com.example.listify.data.database.api.Image
import com.example.listify.data.database.album.AlbumEntity
import com.example.listify.data.database.album.AlbumsRepository
import com.example.listify.data.database.artist.ArtistsRepository
import com.example.listify.ui.screens.AddArtist.AddArtist
import com.example.listify.ui.screens.AddArtist.AddArtistViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.sql.Timestamp


class ArtistDiscographyViewModel(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
    artistRepository: ArtistsRepository
) : ViewModel() {

    private val artistId: String = checkNotNull(savedStateHandle[ArtistDiscographyDestination.itemIdArg])
    var artistName: String by mutableStateOf("")

    private val artistDiscographiesUiState: StateFlow<ArtistDiscographyUiState> =
        artistRepository.getDiscography(artistId).map { ArtistDiscographyUiState(it.map {album ->  album.toAlbum() }) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ArtistDiscographyUiState()
        )

    var uiState: List<AddAlbum> by mutableStateOf(emptyList()) // Initialize with empty list

    fun changeSelect(index: Int) {
        uiState = uiState.toMutableList().apply {
            this[index] = this[index].copy(selected = !this[index].selected, album = this[index].album.copy(hasListened = !this[index].album.hasListened)) // Copy item with updated value
        }
    }
    fun saveSelection() {
        viewModelScope.launch {
            val selected = uiState.map { it.album.toAlbumEntity() }
            selected.forEach{
                albumsRepository.updateAlbum(it)
            }
        }
    }

    init {
        viewModelScope.launch {
            artistRepository.getArtistStream(artistId).collect { artistEntity ->
                if (artistEntity != null) {
                    artistName = artistEntity.artistName
                    Log.d("Name", "Artist name updated: $artistName")
                } else {
                    Log.d("Name", "Artist not found or is null")
                }
            }
        }

        // Collect the album list stream in another launch block
        viewModelScope.launch {
            artistDiscographiesUiState.collect { state ->
                uiState = state.albumList.map { AddAlbum(it, it.hasListened) }
                    .sortedBy { it.album.releaseDate }.asReversed()
                Log.d("Album", "Album list updated: ${uiState.size} items")
            }
        }
    }


    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ListifyApplication)
                val albumsRepository = application.containerDB.albumsRepository
                val artistRepository = application.containerDB.artistRepository
                ArtistDiscographyViewModel(savedStateHandle = this.createSavedStateHandle(), albumsRepository = albumsRepository, artistRepository = artistRepository)
            }
        }
    }
}

data class ArtistDiscographyUiState(val albumList: List<Album> = listOf())

data class AddAlbum(val album: Album, val selected: Boolean)

fun Album.toAlbumEntity(): AlbumEntity = AlbumEntity(
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

fun AlbumEntity.toAlbum(): Album = Album(
    id = id,
    name = albumName,
    releaseDate = releaseYear,
    images = listOf(Image(imgUrl)),
    album_group = albumGroup,
    external_urls = ExternalUrls(spotifyUrl),
    hasListened = hasListened,
    lastUpdate = lastUpdate,
    isListenLater = isListenLater
)

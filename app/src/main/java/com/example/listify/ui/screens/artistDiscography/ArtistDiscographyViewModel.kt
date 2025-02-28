package com.example.listify.ui.screens.artistDiscography

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.repository.AlbumsRepository
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.ui.converters.toAlbumEntity
import com.example.listify.ui.model.ArtistDiscographyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ArtistDiscographyViewModel(
    private val albumsRepository: AlbumsRepository,
    private val artistRepository: ArtistsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val artistId: String = checkNotNull(savedStateHandle[ArtistDiscographyDestination.itemIdArg])

    private val _uiState = MutableStateFlow(ArtistDiscographyUiState())
    val uiState: StateFlow<ArtistDiscographyUiState> = _uiState.asStateFlow()

    fun selectAlbum(albumId: String) {
        _uiState.update { it.copy(selectedAlbum = it.allAlbums.find{album -> album.id == albumId}) }
        _uiState.update { it.copy(selectedAlbum = it.allAlbums.find{album -> album.id == albumId}) }
    }

    fun saveSelection() {
        viewModelScope.launch {
            uiState.value.selectedAlbum?.let { album ->
                albumsRepository.updateAlbum(album.toAlbumEntity().copy(lastUpdate = System.currentTimeMillis()))
            }
        }
    }

    fun changeListened() {
        _uiState.value.selectedAlbum?.let { album ->
            _uiState.update { it.copy(selectedAlbum = album.copy(hasListened = !album.hasListened)) }
        }
    }

    fun changeFavorite() {
        _uiState.value.selectedAlbum?.let { album ->
            _uiState.update { it.copy(selectedAlbum = album.copy(isFavorite = !album.isFavorite)) }
        }
    }

    fun changeListenLater() {
        _uiState.value.selectedAlbum?.let { album ->
            _uiState.update { it.copy(selectedAlbum = album.copy(isListenLater = !album.isListenLater)) }
        }
    }

    fun changeRating(rating: Int) {
        _uiState.value.selectedAlbum?.let { album ->
            if (album.rating == rating) _uiState.update { it.copy(selectedAlbum = album.copy(rating = 0)) }
            else _uiState.update { it.copy(selectedAlbum = album.copy(rating = rating)) }

        }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedAlbum = null) }
    }

    fun deleteSelection() {
        viewModelScope.launch {
            uiState.value.selectedAlbum?.let { album ->
                albumsRepository.deleteAlbum(album.toAlbumEntity())
            }
        }
    }

    init {
        viewModelScope.launch {
            artistRepository.getArtistStream(artistId).collect { artistEntity ->
                if (artistEntity != null) {
                    _uiState.update { it.copy(artistName = artistEntity.artistName) }
                    Log.d("Name", "Artist name updated: ${uiState.value.artistName}")
                }
            }
        }

        viewModelScope.launch {
            artistRepository.getDiscographyUi(artistId)
                .collectLatest { albums ->
                    _uiState.update { it.copy(
                        allAlbums = albums.sortedBy { album -> album.releaseYear }.asReversed(),
                        artistName = it.artistName,
                        listenedAlbums = albums.filter { album -> album.hasListened }) }
                }
        }
    }
}


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

    fun changeSelect(albumId: String) {
        if(uiState.value.selectedAlbums.find { album -> album.id == albumId } == null) {
            val latestSelection = uiState.value.allAlbums.find { it.id == albumId }

            latestSelection?.let { _uiState.update { it.copy(selectedAlbums = it.selectedAlbums + latestSelection) } }
        } else {
            _uiState.update { it.copy(selectedAlbums = it.selectedAlbums.filter { album -> album.id != albumId }) }
        }
    }

    fun saveSelection() {
        viewModelScope.launch {
            uiState.value.selectedAlbums.forEach{
                albumsRepository.updateAlbum(it.toAlbumEntity())
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
                        selectedAlbums = albums.filter { album -> album.hasListened }) }
                }
        }
    }
}


package com.example.listify.ui.screens.editAlbum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.converters.toAlbumUi
import com.example.listify.data.repository.AlbumsRepository
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.ui.converters.toAlbumEntity
import com.example.listify.ui.model.EditAlbumUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditAlbumViewModel(
    private val albumsRepository: AlbumsRepository,
    private val artistRepository: ArtistsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumId: String = checkNotNull(savedStateHandle[EditAlbumScreenDestination.itemIdArg])

    private val _uiState = MutableStateFlow(EditAlbumUiState(null))
    val uiState: StateFlow<EditAlbumUiState> = _uiState.asStateFlow()

    fun updateNote(newNote: String) {
        _uiState.update { it.copy(selectedAlbum = it.selectedAlbum?.copy(note = newNote)) }
    }

    fun saveChanges() {
        viewModelScope.launch {
            uiState.value.selectedAlbum?.let { albumsRepository.updateAlbum(it.toAlbumEntity()) }
        }
    }

    fun deleteAlbum() {
        viewModelScope.launch {
            uiState.value.selectedAlbum?.let { albumsRepository.deleteAlbum(it.toAlbumEntity()) }
        }
    }

    init {
        viewModelScope.launch {
            albumsRepository.getAlbumStream(albumId)
                .collectLatest { album -> _uiState.update { EditAlbumUiState(album?.toAlbumUi()) } }
        }
    }
}
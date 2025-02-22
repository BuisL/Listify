package com.example.listify.ui.screens.discographies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.converters.toArtistUi
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.ui.model.DiscographiesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscographiesViewModel(artistsRepository: ArtistsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscographiesUiState())
    val uiState: StateFlow<DiscographiesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            artistsRepository.getAll()
                .map { artists -> artists.map {artist -> artist.toArtistUi()} }
                .collectLatest { artists ->
                    _uiState.update { it.copy(artistList = artists) }
                }
        }
    }
}




package com.example.listify.ui.screens.discographies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listify.ListifyApplication
import com.example.listify.data.converters.toArtistUi
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.ui.model.ArtistUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DiscographiesViewModel(artistsRepository: ArtistsRepository) : ViewModel() {

    val discographiesUiState: StateFlow<DiscographiesUiState> =
        artistsRepository.getAll().map { DiscographiesUiState(it.map {artist -> artist.toArtistUi() }) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DiscographiesUiState()
        )

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ListifyApplication)
                val artistsRepository = application.containerDB.artistRepository
                DiscographiesViewModel(artistsRepository = artistsRepository)
            }
        }
    }
}

data class DiscographiesUiState(val artistList: List<ArtistUi> = listOf())


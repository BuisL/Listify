package com.example.listify.ui.screens.Discographies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listify.ListifyApplication
import com.example.listify.data.database.api.AAArtist
import com.example.listify.data.database.api.Image
import com.example.listify.data.database.artist.ArtistEntity
import com.example.listify.data.database.artist.ArtistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DiscographiesViewModel(artistsRepository: ArtistsRepository) : ViewModel() {

    val discographiesUiState: StateFlow<DiscographiesUiState> =
        artistsRepository.getAll().map { DiscographiesUiState(it.map {artist ->  artist.toArtist() }) }.stateIn(
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

data class DiscographiesUiState(val artistList: List<AAArtist> = listOf())

fun ArtistEntity.toArtist(): AAArtist = AAArtist(
    id = id,
    name = artistName,
    images = listOf(Image(imageUrl))
)
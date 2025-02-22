package com.example.listify.ui.screens.addArtist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.model.ArtistAlbumCrossRefEntity
import com.example.listify.data.repository.AlbumsRepository
import com.example.listify.data.repository.ArtistAlbumCrossRefRepository
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.network.repository.NetworkSpotifyRepository
import com.example.listify.ui.converters.toAlbumEntity
import com.example.listify.ui.converters.toArtistEntity
import com.example.listify.ui.model.AddArtistUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(kotlinx.coroutines.FlowPreview::class)
class AddArtistViewModel(
    private val spotifyRepository: NetworkSpotifyRepository,
    private val albumsRepository: AlbumsRepository,
    private val artistsRepository: ArtistsRepository,
    private val artistAlbumCrossRefRepository: ArtistAlbumCrossRefRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddArtistUiState())
    val uiState: StateFlow<AddArtistUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(newQuery: String) { _uiState.update { it.copy(query = newQuery) } }

    fun selectAll() { _uiState.update { it.copy(selectedAlbums = it.allAlbums) } }

    fun getAlbums(artistId: String) {
        _uiState.update { it.copy(selectedArtist = it.searchResults.find { artist -> artist.id == artistId }) }
        viewModelScope.launch {
            val response = spotifyRepository.getArtistDiscography(artistId)
            _uiState.update { it.copy(allAlbums = response) }
        }
    }

    fun changeSelect(albumId: String) {

        val latestSelection = uiState.value.allAlbums.find {
            it.id == albumId
        }

        if(uiState.value.selectedAlbums.find { album -> album.id == albumId } == null) {
            latestSelection?.let {
                _uiState.update { it.copy(selectedAlbums = it.selectedAlbums + latestSelection) }
            }
        } else {
            _uiState.update { it.copy(selectedAlbums = it.selectedAlbums.filter { album -> album.id != albumId }) }
        }

        uiState.value.selectedAlbums.forEach{
            Log.d("Updating", it.albumName)
        }
    }

    suspend fun saveSelectedAlbums() {
        //TODO VALIDATE INPUT

        val currentArtist = uiState.value.selectedArtist

        currentArtist?.let { artistsRepository.insertArtist(it.toArtistEntity()) }

        uiState.value.selectedAlbums.forEach { album ->
            albumsRepository.insertAlbum(album.toAlbumEntity())
            currentArtist?.let { it1 -> artistAlbumCrossRefRepository.insert(ArtistAlbumCrossRefEntity(it1.id, album.id)) }
        }
    }

    init {
        viewModelScope.launch {
            uiState
                .map { it.query }
                .debounce(500)  // Wait 500ms after last input before firing API call
                .distinctUntilChanged()  // Avoid duplicate requests for same query
                .collectLatest { query ->
                    val recentSearchResult =
                        if (query.isNotEmpty()) spotifyRepository.searchArtist(query)
                        else emptyList()

                    _uiState.update { it.copy(searchResults = recentSearchResult) }
                }
        }
    }
}


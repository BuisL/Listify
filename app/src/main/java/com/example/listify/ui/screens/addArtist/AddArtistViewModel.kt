package com.example.listify.ui.screens.addArtist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listify.data.model.ArtistAlbumCrossRefEntity
import com.example.listify.data.model.ArtistGenreCrossRefEntity
import com.example.listify.data.repository.AlbumsRepository
import com.example.listify.data.repository.ArtistAlbumCrossRefRepository
import com.example.listify.data.repository.ArtistGenreCrossRefRepository
import com.example.listify.data.repository.ArtistsRepository
import com.example.listify.network.converters.toAlbumUi
import com.example.listify.network.converters.toArtistUi
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
    private val artistAlbumCrossRefRepository: ArtistAlbumCrossRefRepository,
    private val artistGenreCrossRefRepository: ArtistGenreCrossRefRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddArtistUiState())
    val uiState: StateFlow<AddArtistUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(newQuery: String) { _uiState.update { it.copy(query = newQuery) } }

    fun selectAll() { _uiState.update { it.copy(selectedAlbums = it.allAlbums) } }

    fun getAlbums(artistId: String) = viewModelScope.launch {

        val response = spotifyRepository.getArtistDiscography(artistId)

        _uiState.update { it.copy(

            allAlbums = response.map { album -> album.toAlbumUi() },
            albumArtistMap = response.associate { album -> album.toAlbumUi() to album.artists.map { artist -> artist.toArtistUi() } }
        ) }

        uiState.value.albumArtistMap.forEach { (albumUi, artistUis) ->
            Log.d("TEST", albumUi.albumName + " " + artistUis.size)
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
    }

    fun changeLoading() { _uiState.update { it.copy(isLoading = true) } }

    fun saveSelectedAlbums(onCancelNavigation: () -> Unit) = viewModelScope.launch {
        //TODO VALIDATE INPUT
        //TODO Rework make more efficient
        _uiState.update { it.copy(isLoading = true) }
        val currentState = uiState.value

        currentState.selectedAlbums.forEach { album ->
            albumsRepository.insertAlbum(album.toAlbumEntity())
            val entry =
                currentState.albumArtistMap.entries.find { it.key.id == album.id }?.key.let { test -> uiState.value.albumArtistMap[test] }
                    ?: emptyList()


            val artistDataList = spotifyRepository.getArtistsInfo(entry.map {it.id})
            artistDataList.artists.forEach { artist ->
                artistAlbumCrossRefRepository.insert(ArtistAlbumCrossRefEntity(artist.id, album.id))
                artistsRepository.insertArtist(artist.toArtistUi().toArtistEntity())

                artist.genres.forEach { genre ->
                    artistGenreCrossRefRepository.insert(ArtistGenreCrossRefEntity(artist.id, genre))
                }
            }
        }
        _uiState.update { it.copy(isLoading = false) }
        onCancelNavigation()
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


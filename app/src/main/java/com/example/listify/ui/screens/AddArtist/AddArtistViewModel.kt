package com.example.listify.ui.screens.AddArtist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listify.ListifyApplication
import com.example.listify.data.database.api.AAArtist
import com.example.listify.data.Album
import com.example.listify.data.SpotifyRepository
import com.example.listify.data.database.ArtistAlbumCrossRef
import com.example.listify.data.database.ArtistAlbumCrossRefRepository
import com.example.listify.data.database.album.AlbumEntity
import com.example.listify.data.database.album.AlbumsRepository
import com.example.listify.data.database.artist.ArtistEntity
import com.example.listify.data.database.artist.ArtistsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class AddArtist(
    val album: Album,
    var selected: Boolean = false,
)


@OptIn(FlowPreview::class)
class AddArtistViewModel(
    private val spotifyRepository: SpotifyRepository,
    private val albumsRepository: AlbumsRepository,
    private val artistsRepository: ArtistsRepository,
    private val artistAlbumCrossRefRepository: ArtistAlbumCrossRefRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<AAArtist>>(emptyList())  // Holds API results
    val searchResults: StateFlow<List<AAArtist>> = _searchResults.asStateFlow()

    private val _query = MutableStateFlow("")  // Tracks user input

    var uiState: List<AddArtist> by mutableStateOf(emptyList())

    private var selectedArtist: AAArtist? = null

    init {
        viewModelScope.launch {
            _query
                .debounce(500)  // Wait 500ms after last input before firing API call
                .distinctUntilChanged()  // Avoid duplicate requests for same query
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        _searchResults.value = spotifyRepository.searchArtist(query)  // API Call
                    } else {
                        _searchResults.value = emptyList()  // Clear results when input is empty
                    }
                }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _query.value = newQuery  // Update search query
    }

    fun getAlbums(artistId: String) {
        selectedArtist = _searchResults.value.find {
            it.id == artistId
        }
        viewModelScope.launch {
            val response = spotifyRepository.getArtistDiscography(artistId)
            uiState = response.map { AddArtist(it, false) }
        }
    }

    fun changeSelect(index: Int) {
        uiState = uiState.toMutableList().apply {
            this[index] = this[index].copy(selected = !this[index].selected) // Copy item with updated value
        }
    }

    fun selectAll() {
        uiState = uiState.map {
            it.copy(selected = true)
        }
    }

    suspend fun saveSelectedAlbums() {
        //TODO VALIDATE INPUT

        selectedArtist?.let { artistsRepository.insertArtist(it.toArtistEntity()) }

        uiState.forEach {
            albumsRepository.insertAlbum(it.toAlbumEntity())
            if(it.selected) selectedArtist?.let { it1 -> artistAlbumCrossRefRepository.insert(ArtistAlbumCrossRef(it1.id, it.album.id)) }
        }

        uiState.forEach {
            if(it.selected) Log.d("SENDOFF", it.album.name)
        }
        selectedArtist?.let { Log.d("SENDOFF2", it.name ) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ListifyApplication)
                val spotifyRepository = application.container.spotifyRepository
                val albumsRepository = application.containerDB.albumsRepository
                val artistRepository = application.containerDB.artistRepository
                val artistAlbumCrossRefRepository = application.containerDB.artistAlbumsCrossRefRepository
                AddArtistViewModel(
                    spotifyRepository = spotifyRepository,
                    albumsRepository = albumsRepository,
                    artistsRepository = artistRepository,
                    artistAlbumCrossRefRepository = artistAlbumCrossRefRepository)
            }
        }
    }
}

fun AddArtist.toAlbumEntity(): AlbumEntity = AlbumEntity(
    id = album.id,
    albumName = album.name,
    releaseYear = album.releaseDate,
    imgUrl = album.images[0].url,
    albumGroup = album.album_group,
    spotifyUrl = album.external_urls!!.spotify,
    hasListened = false,
    lastUpdate = System.currentTimeMillis(),
    isListenLater = false
)

fun AAArtist.toArtistEntity(): ArtistEntity = ArtistEntity(
    id = id,
    artistName = name,
    imageUrl = images[0].url,
    completionPercent = 0f
)

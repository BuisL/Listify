package com.example.listify.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listify.ListifyApplication
import com.example.listify.ui.screens.addArtist.AddArtistViewModel
import com.example.listify.ui.screens.artistDiscography.ArtistDiscographyViewModel
import com.example.listify.ui.screens.discographies.DiscographiesViewModel
import com.example.listify.ui.screens.editAlbum.EditAlbumViewModel

object ViewModelFactories {

    val DiscographiesFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as ListifyApplication)
            val artistsRepository = application.containerDB.artistRepository
            DiscographiesViewModel(
                artistsRepository = artistsRepository
            )
        }
    }

    val ArtistDiscographyFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as ListifyApplication)
            val albumsRepository = application.containerDB.albumsRepository
            val artistRepository = application.containerDB.artistRepository
            ArtistDiscographyViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                albumsRepository = albumsRepository,
                artistRepository = artistRepository
            )
        }
    }

    val AddArtistFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as ListifyApplication)
            val spotifyRepository = application.container.spotifyRepository
            val albumsRepository = application.containerDB.albumsRepository
            val artistRepository = application.containerDB.artistRepository
            val artistAlbumCrossRefRepository = application.containerDB.artistAlbumsCrossRefRepository
            val artistGenreCrossRefRepository = application.containerDB.artistGenreCrossRefRepository
            AddArtistViewModel(
                spotifyRepository = spotifyRepository,
                albumsRepository = albumsRepository,
                artistsRepository = artistRepository,
                artistAlbumCrossRefRepository = artistAlbumCrossRefRepository,
                artistGenreCrossRefRepository = artistGenreCrossRefRepository,
            )

        }
    }

    val EditAlbumFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as ListifyApplication)
            val albumsRepository = application.containerDB.albumsRepository
            val artistRepository = application.containerDB.artistRepository
            EditAlbumViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                albumsRepository = albumsRepository,
                artistRepository = artistRepository
            )
        }
    }
}


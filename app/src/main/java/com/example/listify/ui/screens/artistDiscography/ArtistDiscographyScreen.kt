package com.example.listify.ui.screens.artistDiscography

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.di.ViewModelFactories
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.components.grids.AlbumGrid

object ArtistDiscographyDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.discography
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ArtistDiscographyScreen(
    modifier: Modifier,
    viewModel: ArtistDiscographyViewModel = viewModel(factory = ViewModelFactories.ArtistDiscographyFactory)
) {
    val artistDiscographyUiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = artistDiscographyUiState.artistName) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AlbumGrid(
                columns = 3,
                modifier = Modifier,
                allAlbums = artistDiscographyUiState.allAlbums,
                selectedAlbums = artistDiscographyUiState.selectedAlbums,
                onClick = { id ->
                    viewModel.changeSelect(id)
                    viewModel.saveSelection()
                }
            )
        }
    }
}
package com.example.listify.ui.screens.discographies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.di.ViewModelFactories
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.components.cards.ArtistCard


object DiscographiesDestination : NavigationDestination {
    override val route = "discographies"
    override val titleRes = R.string.discographies
}

@Composable
fun DiscographiesScreen(
    modifier: Modifier = Modifier,
    navigateToDiscography: (String) -> Unit,
    navigateToAddArtist: () -> Unit,
    viewModel: DiscographiesViewModel = viewModel(factory = ViewModelFactories.DiscographiesFactory)
) {
    val discographiesUiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = "Discographies") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(items = discographiesUiState.artistList) { artist ->
                    ArtistCard(
                        artist = artist,
                        modifier = modifier.padding(4.dp),
                        onItemClick = navigateToDiscography
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = navigateToAddArtist
            ) {
                Text(
                    text = "Add Artist"
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {}
            ) {
                Text(
                    text = "Edit"
                )
            }
        }
    }
}
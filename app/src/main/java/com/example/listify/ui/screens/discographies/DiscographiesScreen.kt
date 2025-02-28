package com.example.listify.ui.screens.discographies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
        topBar = { ListifyTopBar(text = "Discographies") },
        floatingActionButton = { FloatingButtons(navigateToAddArtist)}
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
        }
    }
}

@Composable
fun FloatingButtons(navigateToAddArtist: () -> Unit,) {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            FloatingActionButton(
                onClick = navigateToAddArtist,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Filled.Add, "Add Artist")
            }

            FloatingActionButton(
                onClick = {},
            ) {
                Icon(Icons.Filled.Edit, "Add Artist")
            }
        }

    }

}
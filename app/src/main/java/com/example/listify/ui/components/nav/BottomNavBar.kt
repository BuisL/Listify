package com.example.listify.ui.components.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.listify.ui.model.AddArtistUiState
import com.example.listify.ui.screens.addArtist.AddArtistViewModel
import kotlinx.coroutines.launch

@Composable
fun BottomNavBar(
    viewModel: AddArtistViewModel,
    uiState: AddArtistUiState,
    onCancelNavigation: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    BottomAppBar(
        actions = {
            IconButton(
                onClick = onCancelNavigation,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Rounded.Cancel, contentDescription = "Home")
            }
            IconButton(onClick = { viewModel.selectAll() },
                modifier = Modifier.weight(1f),
                enabled = uiState.allAlbums.isNotEmpty()
            ) {
                Icon(Icons.Filled.Checklist, contentDescription = "Search")
            }
            IconButton(onClick = {
                coroutineScope.launch { viewModel.saveSelectedAlbums()}
                onCancelNavigation()
            },
                modifier = Modifier.weight(1f),
                enabled = uiState.selectedAlbums.isNotEmpty()
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Profile")
            }
        }
    )
}

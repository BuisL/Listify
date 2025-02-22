package com.example.listify.ui.screens.addArtist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.di.ViewModelFactories
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.components.grids.AlbumGrid
import com.example.listify.ui.components.nav.BottomNavBar


object AddArtistDestination : NavigationDestination {
    override val route = "addArtist"
    override val titleRes = R.string.add_artist
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArtistScreen(
    onCancelNavigation: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddArtistViewModel = viewModel(factory = ViewModelFactories.AddArtistFactory)
) {
    val addArtistUiState by viewModel.uiState.collectAsState()

    var expanded by rememberSaveable { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState()

    LaunchedEffect(textFieldState.text) {
        viewModel.onSearchQueryChanged(textFieldState.text.toString())
    }

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = "Add Artist") },
        bottomBar = {
            BottomNavBar(
                viewModel = viewModel,
                uiState = addArtistUiState,
                onCancelNavigation
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            SearchBar(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                inputField = {
                    SearchBarDefaults.InputField(
                        state = textFieldState,
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("E.g. Kendrick Lamar...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the SearchBar
                    .padding(top = 8.dp)
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    addArtistUiState.searchResults.forEach { artist ->
                        ListItem(
                            headlineContent = { Text(artist.artistName) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .clickable {
                                    textFieldState.setTextAndPlaceCursorAtEnd(artist.artistName)
                                    viewModel.getAlbums(artist.id)
                                    expanded = false
                                },
                            leadingContent = {
                                if (artist.imageUrl.isEmpty()){
                                    Icon(Icons.Filled.Person, contentDescription = null)
                                } else {
                                    Box(
                                        modifier = Modifier.size(width = 100.dp, height = 100.dp)
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                .data(artist.imageUrl)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }

            AlbumGrid(
                columns = 3,
                modifier = Modifier.padding(8.dp),
                allAlbums = addArtistUiState.allAlbums,
                selectedAlbums = addArtistUiState.selectedAlbums,
                onClick = { it: String -> viewModel.changeSelect(it) }
            )
        }
    }
}
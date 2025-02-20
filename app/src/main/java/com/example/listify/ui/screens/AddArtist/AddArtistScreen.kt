package com.example.listify.ui.screens.AddArtist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.screens.ArtistDiscography.AlbumCard
import kotlinx.coroutines.launch


object AddArtistDestination : NavigationDestination {
    override val route = "addArtist"
    override val titleRes = R.string.add_artist
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddArtistScreen(
    onCancelNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {

    val artistAddViewModel: AddArtistViewModel = viewModel(factory = AddArtistViewModel.Factory)
    val searchResults by artistAddViewModel.searchResults.collectAsState()

    var expanded by rememberSaveable { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState()

    LaunchedEffect(textFieldState.text) {
        artistAddViewModel.onSearchQueryChanged(textFieldState.text.toString())
    }

    Scaffold(
        bottomBar = { BottomNavBar(
            viewModel = artistAddViewModel,
            onCancelNavigation,
        ) },
        topBar = { ListifyTopBar(text = "Add Artist") }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            SearchBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the SearchBar
                    .padding(top = 8.dp),
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
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    searchResults.forEach { idx ->
                        ListItem(
                            headlineContent = { Text(idx.name) },
                            //supportingContent = { Text("Additional info") },
                            leadingContent = {
                                if (idx.images.isEmpty()){
                                    Icon(Icons.Filled.Person, contentDescription = null)
                                } else {
                                    Box(modifier = Modifier.size(width = 100.dp, height = 100.dp)) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                .data(idx.images[0].url)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                } },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier
                                .clickable {
                                    textFieldState.setTextAndPlaceCursorAtEnd(idx.name)
                                    artistAddViewModel.getAlbums(idx.id)
                                    expanded = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(8.dp),
            ) {
                itemsIndexed(items = artistAddViewModel.uiState) {index, it ->
                    AlbumCard(
                        album = it.album,
                        selected = it.selected,
                        onClick = { artistAddViewModel.changeSelect(index) },
                        modifier = modifier.padding(4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    viewModel: AddArtistViewModel,
    onCancelNavigation: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    BottomAppBar(
        actions = {
            IconButton(onClick = onCancelNavigation, modifier = Modifier.weight(1f)) {
                Icon(Icons.Rounded.Cancel, contentDescription = "Home")
            }
            IconButton(onClick = { viewModel.selectAll() }, modifier = Modifier.weight(1f), enabled = viewModel.uiState.isNotEmpty()) {
                Icon(Icons.Filled.Checklist, contentDescription = "Search")
            }
            IconButton(onClick = {
                coroutineScope.launch { viewModel.saveSelectedAlbums()}
                onCancelNavigation()
                                 },
                modifier = Modifier.weight(1f),
                enabled = (viewModel.uiState.filter { it.selected }).isNotEmpty()) {
                Icon(Icons.Filled.Check, contentDescription = "Profile")
            }
        }
    )
}


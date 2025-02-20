package com.example.listify.ui.screens.Discographies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.data.database.api.AAArtist
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.theme.ListifyTheme


object DiscographiesDestination : NavigationDestination {
    override val route = "discographies"
    override val titleRes = R.string.discographies
}

@Composable
fun DiscographiesScreen(
    modifier: Modifier = Modifier,
    navigateToDiscography: (String) -> Unit,
    navigateToAddArtist: () -> Unit,
    viewModel: DiscographiesViewModel = viewModel(factory = DiscographiesViewModel.Factory)
) {

    val artists by viewModel.discographiesUiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = "Discographies") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                modifier = Modifier.weight(1f).padding(8.dp),

                ) {
                items(items = artists.artistList, key = {artist -> artist.id}) {
                    ArtistCard(
                        artist = it,
                        modifier = modifier.padding(4.dp),
                        onItemClick = navigateToDiscography
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = navigateToAddArtist
            ) {
                Text(
                    text = "Add Artist"
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = {}
            ) {
                Text(
                    text = "Edit"
                )
            }
        }
    }
}

@Composable
fun ArtistCard(
    modifier: Modifier = Modifier,
    artist: AAArtist,
    onItemClick: (String) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onItemClick(artist.id) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 4.dp),

        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(artist.images[0].url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f),
                placeholder = painterResource(R.drawable.loading_img)
            )
            Text(
                text = artist.name,
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}

@Preview
@Composable
fun DiscographiesScreenPreview() {
    ListifyTheme {
        DiscographiesScreen(navigateToDiscography = {}, navigateToAddArtist = {})
    }
}
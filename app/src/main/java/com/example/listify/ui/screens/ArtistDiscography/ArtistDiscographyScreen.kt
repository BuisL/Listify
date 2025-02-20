package com.example.listify.ui.screens.ArtistDiscography

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.data.Album
import com.example.listify.navigation.NavigationDestination

object ArtistDiscographyDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.discography
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ArtistDiscographyScreen(
    artistId: String,
    modifier: Modifier,
    viewModel: ArtistDiscographyViewModel = viewModel(factory = ArtistDiscographyViewModel.Factory)
) {
    val artistDiscographyUiState = viewModel.uiState

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = viewModel.artistName) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                modifier = Modifier,
                ) {
                itemsIndexed(items = artistDiscographyUiState) { index, it ->
                    AlbumCard(
                        album = it.album,
                        modifier = modifier.padding(4.dp),
                        selected = it.selected,
                        onClick = {
                            viewModel.changeSelect(index)
                            viewModel.saveSelection()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    album: Album,
    onClick: () -> Unit,

    ) {
    Card(
        modifier = modifier
            .clickable { onClick() } // Trigger onClick when card is clicked
            .then(if (selected) Modifier.border(4.dp, MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp)) else Modifier), // Add border when selected
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 4.dp),

            ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(album.images[0].url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f),
                placeholder = painterResource(R.drawable.loading_img)
            )
            Text(
                text = album.name,
                modifier = Modifier.padding(4.dp)
                    .height(48.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}
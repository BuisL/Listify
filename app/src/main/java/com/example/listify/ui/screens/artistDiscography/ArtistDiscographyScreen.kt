package com.example.listify.ui.screens.artistDiscography

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.di.ViewModelFactories
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.components.grids.ArtistDiscographyGrid
import com.example.listify.ui.components.items.StarRatingBar

object ArtistDiscographyDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.discography
    const val itemIdArg = "artistId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ArtistDiscographyScreen(
    modifier: Modifier = Modifier,
    viewModel: ArtistDiscographyViewModel = viewModel(factory = ViewModelFactories.ArtistDiscographyFactory),
    navigateToEditAlbumScreen: (String) -> Unit,
) {
    val artistDiscographyUiState by viewModel.uiState.collectAsState()
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp


    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = artistDiscographyUiState.artistName) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            ArtistDiscographyGrid(
                columns = 3,
                modifier = Modifier.padding(8.dp),
                allAlbums = artistDiscographyUiState.allAlbums,
                selectedAlbums = artistDiscographyUiState.listenedAlbums,
                onClick = { id ->
                    viewModel.selectAlbum(id)
                }
            )

            artistDiscographyUiState.selectedAlbum?.let { selectedAlbum ->

                Dialog(onDismissRequest = { viewModel.clearSelection() }) {
                    Card(
                        modifier = Modifier.align(Alignment.Center)
                            .padding(16.dp)
                            .fillMaxWidth(0.9f),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = selectedAlbum.albumName, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(selectedAlbum.imgUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.loading_img),
                                modifier = Modifier.fillMaxWidth()
                                    .aspectRatio(1f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                IconButton(
                                    onClick = {
                                        viewModel.changeListened()
                                        viewModel.saveSelection()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headset,
                                        contentDescription = null,
                                        tint = if (selectedAlbum.hasListened) MaterialTheme.colorScheme.secondaryContainer else Color(0x20FFFFFF),
                                        modifier = Modifier.width(starSize).height(starSize)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        viewModel.changeFavorite()
                                        viewModel.saveSelection()
                                    }
                                ) {
                                    Icon(
                                        imageVector = if(selectedAlbum.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = null,
                                        tint = if (selectedAlbum.isFavorite) Color.Red else Color(0x20FFFFFF),
                                        modifier = Modifier.width(starSize).height(starSize)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        viewModel.changeListenLater()
                                        viewModel.saveSelection()
                                    }
                                ) {
                                    Icon(
                                        imageVector = if(selectedAlbum.isListenLater) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        tint = if (selectedAlbum.isListenLater) Color.Yellow else Color(0x20FFFFFF),
                                        modifier = Modifier.width(starSize).height(starSize)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            StarRatingBar(
                                maxStars = 5,
                                rating = selectedAlbum.rating,
                                onRatingChanged = {
                                    viewModel.changeRating(it)
                                    viewModel.saveSelection()
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                modifier = Modifier.fillMaxWidth(1f)
                                    .padding(horizontal = 8.dp),
                                onClick = {
                                    navigateToEditAlbumScreen(selectedAlbum.id)
                                    viewModel.clearSelection()
                                }) {
                                Text("Edit")
                            }
                        }
                    }
                }
            }
        }
    }
}
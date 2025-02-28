package com.example.listify.ui.screens.editAlbum

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.di.ViewModelFactories
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.model.EditAlbumUiState
import java.util.Date

object EditAlbumScreenDestination : NavigationDestination {
    override val route = "edit_album"
    override val titleRes = R.string.edit
    const val itemIdArg = "albumId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun EditAlbumScreen(
    modifier: Modifier = Modifier,
    viewModel: EditAlbumViewModel = viewModel(factory = ViewModelFactories.EditAlbumFactory),
) {

    val editAlbumUiState: EditAlbumUiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = "Edit Album") }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                editAlbumUiState.selectedAlbum?.let { album ->

                    Text(
                        text = album.albumName
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(album.imgUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.loading_img),
                        modifier = Modifier.aspectRatio(1f)
                    )
                    Text(
                        text = album.releaseYear
                    )
                    Text(
                        text = album.albumGroup
                    )
                    Text(
                        text = album.hasListened.toString()
                    )
                    Text(
                        text = Date(album.lastUpdate).toString()
                    )
                    Text(
                        text = album.isListenLater.toString()
                    )
                    Text(
                        text = album.isFavorite.toString()
                    )
                    Text(
                        text = album.rating.toString()
                    )
                    TextField(
                        value = album.note,
                        onValueChange = { viewModel.updateNote(it) },
                        label = { Text("Note") }
                    )

                    Button(
                        onClick = { viewModel.saveChanges() }
                    ) { Text("Save") }

                    OutlinedButton(
                        onClick = { viewModel.deleteAlbum() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = Color.White)
                    ) { Text("Delete") }
                }
            }
        }
    }
}


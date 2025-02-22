package com.example.listify.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.R
import com.example.listify.ui.model.ArtistUi

@Composable
fun ArtistCard(
    modifier: Modifier = Modifier,
    artist: ArtistUi,
    onItemClick: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onItemClick(artist.id) },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(artist.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f)
            )
            Text(
                text = artist.artistName,
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}
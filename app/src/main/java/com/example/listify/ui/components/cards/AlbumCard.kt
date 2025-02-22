package com.example.listify.ui.components.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.listify.R
import com.example.listify.ui.model.AlbumUi

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    album: AlbumUi,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.clickable { onClick() }
            .then(
                if (selected) {
                    Modifier.border(
                        4.dp,
                        MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else Modifier
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 4.dp),
            ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(album.imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(1f)
            )

            Text(
                text = album.albumName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp)
                    .height(48.dp),
            )
        }

    }
}
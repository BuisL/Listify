package com.example.listify.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 4.dp),
            ) {
            Box(
                modifier = Modifier
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
                        .then(
                            if(selected) Modifier.alpha(0.6f) else Modifier
                        )
                )
                if(selected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomEnd).width(30.dp).height(30.dp)
                    )
                }
            }


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
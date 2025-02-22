package com.example.listify.ui.components.grids

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listify.ui.components.cards.AlbumCard
import com.example.listify.ui.model.AlbumUi

@Composable
fun AlbumGrid(
    modifier: Modifier = Modifier,
    columns: Int = 0,
    allAlbums: List<AlbumUi> = emptyList(),
    selectedAlbums: List<AlbumUi> = emptyList(),
    onClick: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
    ) {
        items(items = allAlbums) {
            AlbumCard(
                album = it,
                selected = selectedAlbums.find {album -> album.id == it.id} != null,
                onClick = { onClick(it.id) },
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}
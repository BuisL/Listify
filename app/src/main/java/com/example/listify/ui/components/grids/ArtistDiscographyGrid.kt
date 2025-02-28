package com.example.listify.ui.components.grids

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listify.ui.components.cards.AlbumCard
import com.example.listify.ui.model.AlbumUi

@Composable
fun ArtistDiscographyGrid(
    modifier: Modifier = Modifier,
    columns: Int = 1,
    allAlbums: List<AlbumUi> = emptyList(),
    selectedAlbums: List<AlbumUi> = emptyList(),
    onClick: (String) -> Unit = {},
) {

    val grouped = allAlbums.groupBy {it.hasListened}
    val sortedGroups = listOf(false, true).mapNotNull { key -> grouped[key]?.let { key to it } }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
    ) {
        sortedGroups.forEach { (category, albumList) ->
            item(span = { GridItemSpan(columns) }) {
                Text(if (category) "Completed" else "Uncompleted")
            }
            items(items = albumList) {
                AlbumCard(
                    album = it,
                    selected = selectedAlbums.find {album -> album.id == it.id} != null,
                    onClick = { onClick(it.id) },
                    modifier = Modifier.padding(4.dp),
                )
            }
        }


    }
}
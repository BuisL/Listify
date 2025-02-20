package com.example.listify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listify.ListifyTopBar
import com.example.listify.R
import com.example.listify.data.Album
import com.example.listify.navigation.NavigationDestination
import com.example.listify.ui.theme.ListifyTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDiscographies: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { ListifyTopBar(text = "Listify") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = navigateToDiscographies,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.discographies)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ListifyTheme {
        HomeScreen(modifier = Modifier.fillMaxSize(), {})
    }
}

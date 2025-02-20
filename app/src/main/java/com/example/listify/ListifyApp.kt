package com.example.listify

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listify.navigation.ListifyNavHost

@Composable
fun ListifyApp(navController: NavHostController = rememberNavController()) {
    ListifyNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListifyTopBar(modifier: Modifier = Modifier, text: String,) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}
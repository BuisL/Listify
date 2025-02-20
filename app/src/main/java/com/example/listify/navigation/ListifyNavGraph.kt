package com.example.listify.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listify.ui.screens.AddArtist.AddArtistDestination
import com.example.listify.ui.screens.AddArtist.AddArtistScreen
import com.example.listify.ui.screens.ArtistDiscography.ArtistDiscographyDestination
import com.example.listify.ui.screens.ArtistDiscography.ArtistDiscographyScreen
import com.example.listify.ui.screens.Discographies.DiscographiesDestination
import com.example.listify.ui.screens.Discographies.DiscographiesScreen
import com.example.listify.ui.screens.HomeDestination
import com.example.listify.ui.screens.HomeScreen

@Composable
fun ListifyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToDiscographies = {
                    navController.navigate(DiscographiesDestination.route)
                }
            )
        }

        composable(route = DiscographiesDestination.route) {
            DiscographiesScreen(
                navigateToDiscography = {
                    navController.navigate("${ArtistDiscographyDestination.route}/${it}")
                },
                navigateToAddArtist = {
                    navController.navigate(AddArtistDestination.route)
                }
            )
        }

        composable(
            route = ArtistDiscographyDestination.routeWithArgs,
            arguments = listOf(navArgument(ArtistDiscographyDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val artistId = navBackStackEntry.arguments?.getString(ArtistDiscographyDestination.itemIdArg)
            if (artistId != null) {
                ArtistDiscographyScreen(artistId = artistId, modifier = Modifier.fillMaxSize())
            }
        }

        composable(route = AddArtistDestination.route)
        {
            AddArtistScreen(
                onCancelNavigation = { navController.navigateUp() }
            )
        }
    }
}
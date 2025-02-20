package com.example.listify.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listify.ui.screens.addArtist.AddArtistDestination
import com.example.listify.ui.screens.addArtist.AddArtistScreen
import com.example.listify.ui.screens.artistDiscography.ArtistDiscographyDestination
import com.example.listify.ui.screens.artistDiscography.ArtistDiscographyScreen
import com.example.listify.ui.screens.discographies.DiscographiesDestination
import com.example.listify.ui.screens.discographies.DiscographiesScreen
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
                ArtistDiscographyScreen(modifier = Modifier.fillMaxSize())
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
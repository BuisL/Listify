package com.example.listify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listify.ui.screens.HomeDestination
import com.example.listify.ui.screens.HomeScreen
import com.example.listify.ui.screens.addArtist.AddArtistDestination
import com.example.listify.ui.screens.addArtist.AddArtistScreen
import com.example.listify.ui.screens.artistDiscography.ArtistDiscographyDestination
import com.example.listify.ui.screens.artistDiscography.ArtistDiscographyScreen
import com.example.listify.ui.screens.discographies.DiscographiesDestination
import com.example.listify.ui.screens.discographies.DiscographiesScreen
import com.example.listify.ui.screens.editAlbum.EditAlbumScreen
import com.example.listify.ui.screens.editAlbum.EditAlbumScreenDestination

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
                ArtistDiscographyScreen(
                    navigateToEditAlbumScreen = { navController.navigate("${EditAlbumScreenDestination.route}/${it}") }
                )
            }
        }

        composable(
            route = EditAlbumScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(EditAlbumScreenDestination.itemIdArg){
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val artistId = navBackStackEntry.arguments?.getString(EditAlbumScreenDestination.itemIdArg)
            if (artistId != null) {
                EditAlbumScreen()
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
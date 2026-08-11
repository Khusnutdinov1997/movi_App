package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.presentation.details.DetailsScreen
import com.example.moviesapp.presentation.screens.HomeScreen
import com.example.moviesapp.utils.Screens

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ){
        composable (Screens.Home.route){
            HomeScreen(
                navController = navController
            )
        }
        composable(Screens.Details.route + "/{movieId}",
            arguments = listOf(navArgument(name = "movieId"){
                type = NavType.IntType
            })
        ) {
            DetailsScreen()
        }
    }
}
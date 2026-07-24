package com.example.moviesapp.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.presentation.components.BottomNavigationBar

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = viewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onEvent = viewModel::onEvent,
                navController = bottomNavController
            )
        }
    ) {paddingValues -> 
        paddingValues
        // TODO:  
    }
}
package com.example.moviesapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesapp.presentation.MovieListState
import com.example.moviesapp.presentation.MovieListUiEvent


@Composable
fun PopularScreen(
    onEvent: (MovieListUiEvent) -> Unit,
    movieListState: MovieListState,
    navHostController: NavHostController
) {
    if (movieListState.popularMovieList.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {

        }
    }
}
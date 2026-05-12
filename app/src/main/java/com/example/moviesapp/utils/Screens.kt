package com.example.moviesapp.utils

sealed class Screens(val route: String) {

    object Home: Screens(route = "main")
    object PopularMovieList: Screens(route = "popularMovieList")
    object UpcomingMovieList: Screens(route = "upcomingMovieList")
    object Details: Screens(route = "details")
}
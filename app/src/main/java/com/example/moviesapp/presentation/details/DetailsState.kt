package com.example.moviesapp.presentation.details

import com.example.moviesapp.moviList.data.domain.model.Movie

data class DetailsState(
    val movie: Movie? = null,
    val isLoading: Boolean = false
)

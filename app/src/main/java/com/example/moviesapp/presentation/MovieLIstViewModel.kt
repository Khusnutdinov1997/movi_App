package com.example.moviesapp.presentation

import androidx.lifecycle.ViewModel
import com.example.moviesapp.moviList.data.domain.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieLIstViewModel @Inject constructor(
    val movieListRepository: MovieListRepository
): ViewModel() {
    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()
}
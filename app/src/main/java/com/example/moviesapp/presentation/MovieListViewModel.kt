package com.example.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.moviList.data.domain.repository.MovieListRepository
import com.example.moviesapp.utils.Category
import com.example.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {
    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    // добавить в конспект патерн сначала офлайн, затем онлайн
    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }

            is MovieListUiEvent.Navigate -> {
                _movieListState.update { currentScreen ->
                    currentScreen.copy(isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen)
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        _movieListState.update { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
                /**
                 * .collectLatest отличается от .collect, тем что, если действие на получение данных
                 * будет запрошено несколько раз, функция запомнит и выполнит только последний, а все
                 * предыдущие будут отменены.
                 * */
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { state ->
                            state.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { state ->
                            state.copy(isLoading = result.loading)
                        }
                    }
                    // Todo Resource.Success
                    is Resource.Success -> {
                        result.data?.let { popularMovieList ->
                            _movieListState.update { state ->
                                state.copy(
                                    popularMovieList = movieListState.value.popularMovieList + popularMovieList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        _movieListState.update { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
                /**
                 * .collectLatest отличается от .collect, тем что, если действие на получение данных
                 * будет запрошено несколько раз, функция запомнит и выполнит только последний, а все
                 * предыдущие будут отменены.
                 * */
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update { state ->
                            state.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update { state ->
                            state.copy(isLoading = result.loading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingMovieList ->
                            _movieListState.update { state ->
                                state.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList + upcomingMovieList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


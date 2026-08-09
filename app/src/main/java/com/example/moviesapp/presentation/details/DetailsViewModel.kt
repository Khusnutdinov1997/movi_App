package com.example.moviesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.moviesapp.moviList.data.domain.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieListRepository,
    savedStateHandle: SavedStateHandle // получает аргументы навигации и сохраняет состояния при пересоздании viewModel
) : ViewModel() {
    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private val movieId = savedStateHandle.get<Int>("movieId") ?: -1

    init {
        getMovie(movieId)
    }
    fun getMovie(id: Int) {
        viewModelScope.launch {
            _detailsState.update { state ->
                state.copy(isLoading = true)
            }
            repository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _detailsState.update { state ->
                            state.copy(movie = result.data, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        _detailsState.update { state ->
                            state.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _detailsState.update { state ->
                            state.copy(isLoading = result.loading)
                        }
                    }
                }
            }
        }
    }
}
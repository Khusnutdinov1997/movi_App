package com.example.moviesapp.moviList.data.domain.repository

import com.example.moviesapp.moviList.data.domain.model.Movie
import com.example.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow

// TODO коорутины ключевые слова kotlin, ознакомится с многопоточностью

interface MovieListRepository {

    suspend fun getMovieList(forceFetchFromRemote: Boolean, category: String, page: Int): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

}
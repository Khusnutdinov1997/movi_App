package com.example.moviesapp.di

import com.example.moviesapp.moviList.data.domain.repository.MovieListRepository
import com.example.moviesapp.moviList.data.domain.repository.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository
}
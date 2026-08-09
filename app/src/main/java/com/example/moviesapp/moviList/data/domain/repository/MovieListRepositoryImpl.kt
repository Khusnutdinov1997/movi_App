package com.example.moviesapp.moviList.data.domain.repository

import coil.network.HttpException
import com.example.moviesapp.moviList.data.domain.model.Movie
import com.example.moviesapp.moviList.data.local.MovieDatabase
import com.example.moviesapp.moviList.data.mappers.toMovie
import com.example.moviesapp.moviList.data.mappers.toMovieEntity
import com.example.moviesapp.moviList.data.remote.MovieApi
import com.example.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(loading = true))
            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(Resource.Success(localMovieList.map { entity ->
                    entity.toMovie(category)
                }))
                emit(Resource.Loading(loading = false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error connection"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Other errors"))
                return@flow
            }

            val movieEntities = movieListFromApi.result.let { dTOS ->
                dTOS.map { dTO -> dTO.toMovieEntity(category) }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(movieEntities.map { movieEntities ->
                movieEntities.toMovie(category)
            }))
            emit(Resource.Loading(loading = false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow{
            emit(Resource.Loading(loading = true))
            val localMovieList = movieDatabase.movieDao.getMovieById(id)

            if(localMovieList != null){
                emit(Resource.Success(localMovieList.toMovie(localMovieList.category)))
                emit(Resource.Loading(loading = false))
                return@flow
            }

            emit(Resource.Error("Not such movie"))
            emit(Resource.Loading(loading = false))
        }

    }
}
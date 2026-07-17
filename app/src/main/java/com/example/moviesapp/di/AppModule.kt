package com.example.moviesapp.di

import androidx.room.Room
import com.example.moviesapp.App
import com.example.moviesapp.moviList.data.local.MovieDatabase
import com.example.moviesapp.moviList.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // TODO: законспектировать Room и Retrofit

    @Provides
    @Singleton
    fun provideDatabase(app: App): MovieDatabase{
       return Room.databaseBuilder(
           context = app,
           klass = MovieDatabase::class.java,
           name = "MovieDatabase"
       ).build()
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi(): MovieApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }
}
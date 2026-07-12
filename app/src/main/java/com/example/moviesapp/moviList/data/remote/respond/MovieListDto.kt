package com.example.moviesapp.moviList.data.remote.respond

data class MovieListDto(
    val page: Int, // номер страницы
    val result: List<MovieDTO>, // список фильмов
    val total_pages: Int, // общее количество страниц на сайте
    val total_results: Int // количестов запрошенных фильмов
)

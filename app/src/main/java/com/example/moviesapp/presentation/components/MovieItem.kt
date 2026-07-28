package com.example.moviesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesapp.moviList.data.domain.model.Movie
import com.example.moviesapp.moviList.data.remote.MovieApi
import com.example.moviesapp.utils.Screens

@Composable
fun MovieItem(
    movie: Movie,
    navHostController: NavHostController
) {
    val posterPath = movie.poster_path
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + posterPath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember { mutableStateOf(defaultColor) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(brush = Brush.verticalGradient(colors = listOf(
                MaterialTheme.colorScheme.secondaryContainer,
                dominantColor
            )))
            .clickable{navHostController.navigate(Screens.Details.route + "/${movie.id}")}
    ) { }
}
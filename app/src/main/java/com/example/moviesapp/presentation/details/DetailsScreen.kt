package com.example.moviesapp.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesapp.moviList.data.remote.MovieApi
import com.example.moviesapp.utils.RatingBar

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState = viewModel.detailsState.collectAsState().value
    val backdropImagePath = detailsState.movie?.backdrop_path.orEmpty()
    val posterImagePath = detailsState.movie?.poster_path.orEmpty()

    val backdropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + backdropImagePath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + posterImagePath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            when {
                backdropImagePath.isEmpty() || backdropImageState is AsyncImagePainter.State.Error -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ImageNotSupported,
                            modifier = Modifier.size(70.dp),
                            contentDescription = null
                        )
                    }
                }

                backdropImageState is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = backdropImageState.painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                }
            }
        }
        detailsState.movie?.let { movie ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.overview,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    RatingBar(
                        starsModifier = Modifier.size(18.dp),
                        rating = movie.vote_average / 2
                    )
                    Text(
                        text = movie.vote_average.toString().take(3),
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Language" + movie.original_language,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Release data" + movie.release_date,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Votes" + movie.vote_count,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Overview",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        detailsState.movie?.let { movie ->
            Text(
                text = movie.overview,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
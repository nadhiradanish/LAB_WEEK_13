package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "39a12be7e39b651f2a09d6d96f6985da"
    // LiveData that contains a list of movies
    // fetch movies from the API
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
// emit the list of popular movies from the API
            emit(movieService.getPopularMovies(apiKey).results)
// use Dispatchers.IO to run this coroutine on a shared pool of
        }.flowOn(Dispatchers.IO)
    }

}
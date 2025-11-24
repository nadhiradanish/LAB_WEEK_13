package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository)
    : ViewModel() {

    init {
        fetchPopularMovies()
    }

    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    // fetch movies from the API
    private fun fetchPopularMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieRepository.fetchMovies()
                .catch { e ->
                    // catch is a terminal operator that catches exceptions from the Flow
                    _error.value = "An exception occurred: ${e.message}"
                }
                .collect { movies ->
                    // collect is a terminal operator that collects the values from the Flow
                    // the results are emitted to the StateFlow
                    _popularMovies.value = movies
                }
        }
    }
}

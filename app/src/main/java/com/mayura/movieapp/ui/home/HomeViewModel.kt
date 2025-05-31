package com.mayura.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = Repository()
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        getLatestMovies()
    }

    private fun getLatestMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getLatestMovies()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _movies.value = it.results
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}

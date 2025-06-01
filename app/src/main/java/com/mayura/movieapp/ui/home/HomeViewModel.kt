package com.mayura.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayura.movieapp.data.model.Genre
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.*

class HomeViewModel : ViewModel() {

    private val repository = Repository()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _allMovies = mutableListOf<Movie>()

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres


    init {
        loadLatestMovies()

        viewModelScope.launch {
            val genreList = repository.getGenres()
            _genres.value = genreList
        }
    }

    fun loadLatestMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getLatestMovies()
                if (response.isSuccessful) {
                    _allMovies.clear()
                    response.body()?.results?.let { _allMovies.addAll(it) }
                    _movies.value = _allMovies.toList()
                    _error.value = null
                }
                else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "An unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByGenre(genreName: String) {
        if (genreName == "All") {
            _movies.value = _allMovies.toList()
        } else {
            viewModelScope.launch {
                val genres = repository.getGenres()
                val genreId = genres.find { it.name.equals(genreName, ignoreCase = true) }?.id
                genreId?.let { id ->
                    _movies.value = _allMovies.filter { movie -> movie.genreIds.contains(id) }
                }
            }
        }
    }
}
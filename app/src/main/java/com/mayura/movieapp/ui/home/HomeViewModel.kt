package com.mayura.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.*

class HomeViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val repository = Repository()

    // Store all fetched movies for reuse when filtering
    private var allMovies: List<Movie> = emptyList()

    // Simplified genre map for your 3 genres
    private val genreMap = mapOf(
        28 to "Action",
        35 to "Comedy",
        18 to "Drama"
    )

    fun loadMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLatestMovies()
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    allMovies = results
                    _movies.postValue(results) // initially show all
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterByGenre(genre: String) {
        if (genre == "All") {
            _movies.postValue(allMovies)
        } else {
            val genreId = genreMap.filterValues { it == genre }.keys.firstOrNull()
            if (genreId != null) {
                val filtered = allMovies.filter { it.genreIds.contains(genreId) }
                _movies.postValue(filtered)
            } else {
                _movies.postValue(emptyList())
            }
        }
    }
}
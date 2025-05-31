package com.mayura.movieapp.ui.search

import androidx.lifecycle.*
import com.mayura.movieapp.data.api.RetrofitInstance
import com.mayura.movieapp.data.model.Movie
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> get() = _searchResults

    private val apiKey = "14f7c50d6a82c630262cf1de7f4b85ca"

    fun searchMovies(query: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.searchMovies(apiKey, query, 1)
            if (response.isSuccessful) {
                _searchResults.value = response.body()?.results ?: emptyList()
            }
        }
    }
}
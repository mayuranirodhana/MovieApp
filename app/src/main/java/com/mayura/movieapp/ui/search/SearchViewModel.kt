package com.mayura.movieapp.ui.search

import androidx.lifecycle.*
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.data.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = Repository()

    private val _results = MutableLiveData<List<Movie>>()
    val results: LiveData<List<Movie>> get() = _results

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            if (query.isNotBlank()) {
                try {
                    val response = repository.searchMovies(query)
                    if (response.isSuccessful) {
                        _results.postValue(response.body()?.results ?: emptyList())
                    } else {
                        _results.postValue(emptyList())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _results.postValue(emptyList())
                }
            } else {
                _results.postValue(emptyList())
            }
        }
    }

}
package com.mayura.movieapp.data.repository

import RetrofitInstance
import com.mayura.movieapp.data.model.MovieResponse
import retrofit2.Response

class Repository {
    private val apiKey = "14f7c50d6a82c630262cf1de7f4b85ca"

    suspend fun getLatestMovies(page: Int = 1): Response<MovieResponse> {
        return RetrofitInstance.api.getLatestMovies(apiKey = apiKey, page = page)
    }

    suspend fun searchMovies(query: String, page: Int = 1): Response<MovieResponse> {
        return RetrofitInstance.api.searchMovies(
            apiKey = apiKey,
            query = query,
            page = page
        )
    }
}

package com.renatooc.apptdc.data.api

import com.renatooc.apptdc.data.model.Movie
import kotlinx.coroutines.Deferred
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPopularMovies(): MutableList<Movie> {
        return apiService.getPopularMovies()
    }

    override suspend fun getMovie(id: Int): Movie? {
        return apiService.getMovieById(id)
    }
}
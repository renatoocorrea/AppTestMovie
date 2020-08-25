package com.renatooc.apptdc.data.api

import android.util.Log
import com.renatooc.apptdc.data.model.Movie
import com.renatooc.apptdc.data.repository.BaseRepository
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper, BaseRepository() {

    override suspend fun getPopularMovies() : MutableList<Movie>? {

        //Call is at BaseRepository.kt
        val movieResponse = safeApiCall(
            call = {apiService.getPopularMovies().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return movieResponse?.results?.toMutableList()

    }

    override suspend fun getMovie(id: Int): Movie? {

        return safeApiCall(
            call = { apiService.getMovieById(id).await() },
            errorMessage = "Error Fetching Movie with Id: $id"
        )
    }

}
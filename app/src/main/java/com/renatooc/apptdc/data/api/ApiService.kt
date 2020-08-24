package com.renatooc.apptdc.data.api

import com.renatooc.apptdc.data.model.Movie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //Get popular movies list as a response.
    @GET("movie/popular")
    fun getPopularMovies(): MutableList<Movie>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id:Int): Movie
}

// Just to retrive the movies list from the request @get.
data class MovieResponse(
    val results: List<Movie>,
    val result: Movie
)
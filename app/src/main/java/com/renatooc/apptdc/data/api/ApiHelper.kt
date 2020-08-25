package com.renatooc.apptdc.data.api

import com.renatooc.apptdc.data.model.Movie

interface ApiHelper {

    suspend fun getPopularMovies(): MutableList<Movie>?

    suspend fun getMovie(id: Int): Movie?

}
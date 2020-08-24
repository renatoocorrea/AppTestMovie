package com.renatooc.apptdc.data.repository

import com.renatooc.apptdc.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getMovie(id: Int) = apiHelper.getMovie(id)
}
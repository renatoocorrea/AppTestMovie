package com.renatooc.apptdc.ui.main

import com.renatooc.apptdc.data.model.Movie

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class Movies(val user: MutableList<Movie>?) : MainState()
    data class MovieSingle(val movie: Movie?) : MainState()
    data class Error(val error: String?) : MainState()

}
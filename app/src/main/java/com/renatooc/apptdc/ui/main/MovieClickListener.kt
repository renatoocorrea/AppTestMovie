package com.renatooc.apptdc.ui.main

import com.renatooc.apptdc.data.model.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}
package com.renatooc.apptdc.ui.main

import android.widget.ImageView
import com.renatooc.apptdc.data.model.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}
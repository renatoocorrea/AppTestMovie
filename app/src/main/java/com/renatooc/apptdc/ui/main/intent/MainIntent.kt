package com.renatooc.apptdc.ui.main.intent

sealed class MainIntent {

    object FetchPopularMovies : MainIntent()
    object FetchMovie : MainIntent()

}
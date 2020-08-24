package com.renatooc.apptdc.ui.main.intent

sealed class MainIntent {

    object FetchMovie : MainIntent()

    object FetchPopularMovies : MainIntent()

}
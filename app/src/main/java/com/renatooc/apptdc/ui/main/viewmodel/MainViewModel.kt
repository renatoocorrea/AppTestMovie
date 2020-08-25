package com.renatooc.apptdc.ui.main.viewmodel

import android.util.Log
import com.renatooc.apptdc.data.repository.MainRepository
import com.renatooc.apptdc.ui.main.intent.MainIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatooc.apptdc.ui.main.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.net.IDN

@ExperimentalCoroutinesApi
class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchPopularMovies -> fetchPopularMovies()
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Movies(repository.getPopularMovies())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
            Log.e("TESTE", "TESTE" + _state.value.toString())
        }
    }

     fun fetchMovie(id: Int) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.MovieSingle(repository.getMovie(id))
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
            Log.e("TESTE", "TESTE" + _state.value.toString())
        }
    }
}
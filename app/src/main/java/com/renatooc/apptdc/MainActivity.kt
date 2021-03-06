package com.renatooc.apptdc

import android.os.Bundle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatooc.apptdc.data.api.ApiHelperImpl
import com.renatooc.apptdc.data.api.TmdbFactory
import com.renatooc.apptdc.data.model.Movie
import com.renatooc.apptdc.ui.main.MainState
import com.renatooc.apptdc.ui.main.MovieClickListener
import com.renatooc.apptdc.ui.main.adapter.MainAdapter
import com.renatooc.apptdc.ui.main.intent.MainIntent
import com.renatooc.apptdc.ui.main.viewmodel.MainViewModel
import com.renatooc.apptdc.util.Constants.Companion.MOVIE_BASE_URL
import com.renatooc.apptdc.util.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchPopularMovies)
            }
        }
    }


    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    TmdbFactory.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
               render(it)
            }
        }
    }

    fun render(state: MainState){
        when (state) {
            is MainState.Idle -> {
                //
            }
            is MainState.Loading -> {
                buttonFetchUser.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                movieImage.visibility = View.GONE
                movieTitle.visibility = View.GONE
                movieResume.visibility = View.GONE
            }

            is MainState.Movies -> {
                progressBar.visibility = View.GONE
                buttonFetchUser.visibility = View.GONE
                movieImage.visibility = View.GONE
                movieTitle.visibility = View.GONE
                movieResume.visibility = View.GONE
                renderList(state.moviesList)
            }
            is MainState.Error -> {
                progressBar.visibility = View.GONE
                buttonFetchUser.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, state.error, Toast.LENGTH_LONG).show()
                movieImage.visibility = View.GONE
                movieTitle.visibility = View.GONE
                movieResume.visibility = View.GONE
            }
            is MainState.MovieSingle -> {
                progressBar.visibility = View.GONE
                buttonFetchUser.visibility = View.GONE
                movieImage.visibility = View.VISIBLE
                movieTitle.visibility = View.VISIBLE
                movieResume.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                renderMovie(state.movie)
            }
        }
    }
    private fun renderList(users: MutableList<Movie>?) {
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }

    private fun renderMovie(movie: Movie?){
        movieTitle.text = movie?.title
        Picasso.get().load(MOVIE_BASE_URL + movie?.poster_path)
            .placeholder(R.drawable.ic_launcher_background)
            .into(movieImage)
        movieResume.text = movie?.overview
    }

    override fun onMovieClick(movie: Movie) {
        mainViewModel.fetchMovie(movie.id)
    }
}
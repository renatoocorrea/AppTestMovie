package com.renatooc.apptdc

import com.renatooc.apptdc.data.model.Cast
import com.renatooc.apptdc.data.model.Credits
import com.renatooc.apptdc.data.model.Movie
import com.renatooc.apptdc.data.model.MovieGenre
import com.renatooc.apptdc.data.repository.MainRepository
import com.renatooc.apptdc.ui.main.MainState
import com.renatooc.apptdc.ui.main.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Test

import org.junit.Before

@ExperimentalCoroutinesApi
class MainViewModelTest(
) {
    val repository = mockkClass(MainRepository::class)

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun movieStateData() = dispatcher.runBlockingTest {
        //arrange
        val viewModel = MainViewModel(repository)
        coEvery { dispatcher.run { repository.getPopularMovies() } } returns mockedList()

        //act
        viewModel.fetchPopularMovies()

        //assert
        val stateValue = viewModel.state.value
        Assert.assertTrue(stateValue is MainState.Movies)
    }

    @Test
    fun movieSingleStateData() = dispatcher.runBlockingTest {
        //arrange
        val viewModel = MainViewModel(repository)
        coEvery { dispatcher.run { repository.getMovie(1) } } returns mockMovie()

        //act
        viewModel.fetchMovie(1)

        //Assert
        val stateValue = viewModel.state.value
        Assert.assertTrue(stateValue is MainState.MovieSingle)
    }

    @Test
    fun movieErrorFetching() = dispatcher.runBlockingTest {
        //arrange
        val viewModel = MainViewModel(repository)
        coEvery { dispatcher.run { repository.getPopularMovies() } } returns mutableListOf<Movie>()

        //act
        viewModel.fetchPopularMovies()

        //assert
        val stateValue = viewModel.state.value
        Assert.assertTrue(stateValue is MainState.Movies)
    }




    private fun mockedList():MutableList<Movie> {
        val list = mutableListOf<Movie>()
        list.add(0, Movie(1, "Título", "Esse é um filme legal",mockGenre(),"18-10-2019",
            "path","180min", mockCredits()))
        return list
    }

    private fun mockMovie() : Movie{
        return Movie(1, "Título", "Esse é um filme legal",mockGenre(),"18-10-2019",
            "path","180min", mockCredits())
    }

    private fun mockGenre(): Array<MovieGenre>{
        val genre:ArrayList<MovieGenre> = arrayListOf()
        val array :Array<MovieGenre> = arrayOf()
        genre.add(MovieGenre(2, "Terror"))
        genre.add(MovieGenre(2, "Romance"))
        genre.add(MovieGenre(2, "Comedia"))
        return genre.toArray(array)
    }

    private fun mockCredits(): Credits{
        val cast = arrayListOf<Cast>()
        cast.add( Cast(1, "Capitão Louco","2424", "Nome", "profile_path"))
        cast.add( Cast(1, "Capitão feliz","5353", "Feliz", "profile_path"))
        val toArray: Array<Cast> = arrayOf()
        return Credits(cast.toArray(toArray))
    }

}
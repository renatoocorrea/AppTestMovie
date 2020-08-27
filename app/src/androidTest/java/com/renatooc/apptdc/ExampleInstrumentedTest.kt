package com.renatooc.apptdc

import android.app.Activity
import android.view.WindowManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.renatooc.apptdc.data.model.Cast
import com.renatooc.apptdc.data.model.Credits
import com.renatooc.apptdc.data.model.Movie
import com.renatooc.apptdc.data.model.MovieGenre
import com.renatooc.apptdc.ui.main.MainState
import com.renatooc.apptdc.ui.main.intent.MainIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityRenderer {
    @ExperimentalCoroutinesApi
    @Rule
    @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java)

    lateinit private var delegate: Delegate

    @Before
    fun setUp() {
        activityRule.activity.wakeUpDevice()
        onView(withId(R.id.buttonFetchUser))
            .perform(ViewActions.click())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun starting() {
        delegate = Delegate(activityRule, MainState.Idle)
        delegate.renderStateAndAssert()
    }

    @Test
    fun moviesShowingState() {
        delegate = Delegate(activityRule, MainState.Movies(mockedList()))
        delegate.renderStateAndAssert()
    }

    @Test
    fun movieShowingState() {
        delegate = Delegate(activityRule, MainState.MovieSingle(mockMovie()))
        delegate.renderStateAndAssert()
    }

    private fun Activity.wakeUpDevice() {
        IdlingPolicies.setIdlingResourceTimeout(10, TimeUnit.MINUTES)

        this.runOnUiThread {
            this.window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private fun mockedList():MutableList<Movie> {
        val list = mutableListOf<Movie>()
        list.add(0, Movie(1, "Título", "Esse é um filme legal",mockGenre(),"18-10-2019",
            "path","180min", mockCredits())
        )
        return list
    }

    private fun mockMovie() : Movie {
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

    private fun mockCredits(): Credits {
        val cast = arrayListOf<Cast>()
        cast.add( Cast(1, "Capitão Louco","2424", "Nome", "profile_path"))
        cast.add( Cast(1, "Capitão feliz","5353", "Feliz", "profile_path"))
        val toArray: Array<Cast> = arrayOf()
        return Credits(cast.toArray(toArray))
    }


}

/**
 * Runs the test
 * @param state state that will be rendered onto the view, and then verified that it is rendered properly
 */
class Delegate(
    private val rule: ActivityTestRule<MainActivity>,
    private var state: MainState = MainState.Idle

) {
    val activity: MainActivity by lazy { rule.activity }
    val ui by lazy { activity }

    @ExperimentalCoroutinesApi
    fun renderState(state: MainState.Idle? = null) {
        if (state != null) {
            this.state = state
        }

        ui.render(this.state)
    }

    fun renderStateAndAssert(state: MainState.Loading? = null) {
        if (state != null) {
            this.state = state
        }

        ui.render(this.state)
        assert(this.state is MainState.Loading)
    }

}

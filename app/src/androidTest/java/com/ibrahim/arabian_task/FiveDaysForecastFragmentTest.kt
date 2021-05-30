package com.ibrahim.arabian_task


import android.view.Gravity
import android.view.View
import androidx.room.PrimaryKey
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.transition.Slide
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.activity.MainActivity
import com.ibrahim.arabian_task.forcast.presentation.view.fragment.ForecastResultFragment
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastRemoteViewModel
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast_result.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@LargeTest
@RunWith(AndroidJUnit4::class)
class FiveDaysForecastFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    val adapter by lazy { activity.adapter }
    val viewmodel by lazy { noteDetailsFragment.viewModel }

    lateinit var noteDetailsFragment: ForecastResultFragment
    lateinit var activity: MainActivity

    val list by lazy {
        Utils.getForecastUiModeList()
    }

    init {
        activity = mActivityTestRule.activity
        navigateToForecastFragment(list[0])
    }

    private fun navigateToForecastFragment(forecastUiModel: ForecastUiModel) {
        noteDetailsFragment = ForecastResultFragment(forecastUiModel.name)
        noteDetailsFragment.enterTransition = Slide(Gravity.END)

        mActivityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, noteDetailsFragment)
            .addToBackStack(noteDetailsFragment::class.java.name)
            .commit()
    }



    @Test
    fun test_loading_visibility() {
        navigateToForecastFragment(list[0])
        //test loading visibility
        viewmodel.screenState.postValue(ForecastRemoteViewModel.ForecastScreenState.Loading)
        onView(withId(R.id.constraintLayout)).check(matches(isDisplayed()))

        viewmodel.screenState.postValue(ForecastRemoteViewModel.ForecastScreenState.Loading)

    }
}

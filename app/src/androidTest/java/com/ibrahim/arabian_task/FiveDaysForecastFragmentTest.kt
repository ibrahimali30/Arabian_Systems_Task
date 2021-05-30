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
import com.ibrahim.arabian_task.extensions.timeStampToFormattedString
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import com.ibrahim.arabian_task.forcast.presentation.view.activity.MainActivity
import com.ibrahim.arabian_task.forcast.presentation.view.fragment.ForecastResultFragment
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastRemoteViewModel
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast_result.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
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
    val viewmodel by lazy { noteDetailsFragment.viewModel }
    lateinit var noteDetailsFragment: ForecastResultFragment
    val list by lazy {
        Utils.getForecastUiModeList()
    }

    @Before
    fun setUp(){
        mActivityTestRule.activity
        navigateToForecastFragment(list[0])
        Thread.sleep(1000)
    }

    private fun navigateToForecastFragment(forecastUiModel: ForecastUiModel) {
        noteDetailsFragment = ForecastResultFragment(forecastUiModel.name)

        mActivityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, noteDetailsFragment)
            .addToBackStack(noteDetailsFragment::class.java.name)
            .commit()
    }



    @Test
    fun test_loading_visibility() {
        //test loading visibility
        viewmodel.screenState.postValue(ForecastRemoteViewModel.ForecastScreenState.Loading)
        onView(withId(R.id.constraintLayout)).check(matches(isDisplayed()))
        viewmodel.screenState.postValue(ForecastRemoteViewModel.ForecastScreenState.Loading)

    }

    @Test
    fun test_city_name_title_visibility() {
        //test loading visibility
        onView(withId(R.id.tvTitle)).check(matches(withText(noteDetailsFragment.cityName)))
        viewmodel.screenState.postValue(ForecastRemoteViewModel.ForecastScreenState.Loading)

    }

    @Test
    fun test_binding_data_to_recycle_view_from_remote_source() {
        //test binding data to recycler view from remote source
        val forecastModelRemote = list[2]
        viewmodel.screenState.postValue(
            ForecastRemoteViewModel.ForecastScreenState.SuccessAPIResponse(listOf(forecastModelRemote))
        )
        //check forecast name
        val tvCityname = onView(getView(R.id.tvDate, forecastModelRemote.dt.timeStampToFormattedString()))
        tvCityname.check(matches(withText(forecastModelRemote.dt.timeStampToFormattedString())))

        //check forecast main
        val tvMain = onView(getView(R.id.tvMain, forecastModelRemote.main))
        tvMain.check(matches(withText(forecastModelRemote.main)))

        //check forecast description
        val tvDescription = onView(getView(R.id.tvDescription, forecastModelRemote.description))
        tvDescription.check(matches(withText(forecastModelRemote.description)))

        //check forecast temp
        val tvTemp = onView(getView(R.id.tvTemp, "${forecastModelRemote.temp.toInt()}°C"))
        tvTemp.check(matches(withText("${forecastModelRemote.temp.toInt()}°C")))
    }

    @Test
    fun test_error_view_visibility() {
        // on Error check if error view and retry button is displayed
        viewmodel.screenState.postValue(
            ForecastRemoteViewModel.ForecastScreenState.ErrorLoadingFromApi(Exception("test exception msg"))
        )
        onView(
            withId(R.id.errorViewLayoutFragment)
        ).check(matches(isDisplayed()))
    }


    private fun getView(id: Int, text: String): Matcher<View>? {
        return allOf(
            withId(id), withText(text)
        )
    }
}

package com.ibrahim.arabian_task


import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ibrahim.arabian_task.forcast.presentation.view.activity.MainActivity
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastViewModel
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    val adapter by lazy { mActivityTestRule.activity.adapter }
    val viewmodel by lazy { mActivityTestRule.activity.viewModel }

    val list by lazy {
        Utils.getForecastUiModeList()
    }


    @Test
    fun test_loading_visibility() {
        //test loading visibility
        viewmodel.screenState.postValue(ForecastViewModel.ForecastScreenState.Loading)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_binding_data_to_recycle_view_from_local_source() {
        //test binding data to recycler view from local source
        val forecastModelLocal = list[0]
        viewmodel.screenState.postValue(
            ForecastViewModel.ForecastScreenState.SuccessAPIResponse(list)
        )
        //check forecast name
        val tvCityname = onView(getView(R.id.tvCityname, forecastModelLocal.name))
        tvCityname.check(matches(withText(forecastModelLocal.name)))

        //check forecast main
        val tvMain = onView(getView(R.id.tvMain, forecastModelLocal.main))
        tvMain.check(matches(withText(forecastModelLocal.main)))

        //check forecast description
        val tvDescription = onView(getView(R.id.tvDescription, forecastModelLocal.description))
        tvDescription.check(matches(withText(forecastModelLocal.description)))

        //check forecast temp
        val tvTemp = onView(getView(R.id.tvTemp, "${forecastModelLocal.temp.toInt()}°C"))
        tvTemp.check(matches(withText("${forecastModelLocal.temp.toInt()}°C")))

    }

    private fun getView(id: Int, text: String): Matcher<View>? {
        return allOf(
            withId(id), withText(text)
        )
    }

    @Test
    fun test_binding_data_to_recycle_view_from_remote_source() {
        //test binding data to recycler view from remote source
        val forecastModelRemote = list[2]
        viewmodel.screenState.postValue(
            ForecastViewModel.ForecastScreenState.SuccessAPIResponse(listOf(forecastModelRemote))
        )
        //check forecast name
        val tvCityname = onView(getView(R.id.tvCityname, forecastModelRemote.name))
        tvCityname.check(matches(withText(forecastModelRemote.name)))

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
            ForecastViewModel.ForecastScreenState.ErrorLoadingFromApi(Exception("test exception msg"))
        )
        onView(withId(R.id.tvErrorMsg)).check(matches(isDisplayed()))
        onView(withId(R.id.btRetry)).check(matches(isDisplayed()))
        // TODO: 5/30/2021 test btRetry on click
    }

    @Test
    fun test_no_stored_data_view_visibility() {
        // simulate empty list
        viewmodel.screenState.postValue(ForecastViewModel.ForecastScreenState.SuccessAPIResponse(listOf()))

        onView(withId(R.id.tvNoSoredData)).check(matches(isDisplayed()))
    }
}

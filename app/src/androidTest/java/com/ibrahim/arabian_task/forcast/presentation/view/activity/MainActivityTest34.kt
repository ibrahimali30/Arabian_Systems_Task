package com.ibrahim.arabian_task.forcast.presentation.view.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.Utils
import com.ibrahim.arabian_task.forcast.presentation.viewmodel.ForecastViewModel
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest34 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    val adapter by lazy { mActivityTestRule.activity.adapter }
    val viewmodel by lazy { mActivityTestRule.activity.viewModel }

    val list by lazy {
        Utils.getForecastUiModeList()
    }
    @Test
    fun mainActivityTest34() {
        val wordModelLocal = list[0]
        viewmodel.screenState.postValue(
            ForecastViewModel.ForecastScreenState.SuccessAPIResponse(listOf(wordModelLocal))
        )


        val textView = onView(
            allOf(
                withId(R.id.tvCityname), withText(wordModelLocal.name),
                withParent(
                    allOf(
                        withId(R.id.llMain),
                        withParent(withId(R.id.rootView))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText(wordModelLocal.name)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

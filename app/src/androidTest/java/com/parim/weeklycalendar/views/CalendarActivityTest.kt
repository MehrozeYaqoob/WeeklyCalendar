package com.parim.weeklycalendar.views

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.parim.weeklycalendar.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class CalendarActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<CalendarActivity>()

    @Before
    fun setUp() {
        val scenario = launchActivity<CalendarActivity>()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun isActivityInView() {
        Espresso.onView(ViewMatchers.withId(R.id.daySpinner))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
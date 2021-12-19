package com.parim.weeklycalendar.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.parim.weeklycalendar.repositories.CalendarRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class CalendarViewModelTest {
    private lateinit var viewModel: CalendarViewModel
    private lateinit var calendarRepository: CalendarRepository
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        calendarRepository = mock()
        viewModel = CalendarViewModel(calendarRepository)
    }

    @Test
    fun testGetHolidayLiveData() {
        viewModel.holidayLiveData.observeForever {
            it.isEmpty()
        }
    }


    @Test
    fun testOnLoadData() {
        viewModel.onLoadData(context, Date(1639850888717))
    }

    @Test
    fun testTriggerErrorEvent() {
        viewModel.triggerErrorEvent(mock())
    }
}
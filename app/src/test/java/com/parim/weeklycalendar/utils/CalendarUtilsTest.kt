package com.parim.weeklycalendar.utils

import android.content.Context
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class CalendarUtilsTest {
    private lateinit var context: Context

    @Before
    fun setUp() {

    }

    @Test
    fun testGetDB_DATE_FORMAT() {
        Truth.assertThat(
            CalendarUtils.Companion.getGmt(Date(1639897462494))
        ).isEqualTo("19/Dec/2021 12:04:22")
    }

    @Test
    fun dateStringFromFormat() {
        Truth.assertThat(CalendarUtils.dateStringFromFormat(
            date = Date(1639897462494),
            format = "dd/MMM/yyyy HH:mm:ss"
        )).isEqualTo("19/Dec/2021 12:04:22")
    }
}
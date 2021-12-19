package com.parim.weeklycalendar.manager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PreferenceManagerTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testSetLastUpdatedDate() {
        PreferenceManager.setLastUpdatedDate(context, 1639850888717)
    }

    @Test
    fun testGetLastUpdatedDate() {
        testSetLastUpdatedDate()
        Truth.assertThat(
            PreferenceManager.getLastUpdatedDate(context, 0)
        ).isEqualTo(1639850888717)
    }
}
package com.parim.weeklycalendar.diffutils

import com.google.common.truth.Truth
import com.parim.weeklycalendar.model.FilteredRealmDTO
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HolidayDiffUtilTest {
    private lateinit var holidayDiffUtil: HolidayDiffUtil

    @Before
    fun setUp() {
        holidayDiffUtil = HolidayDiffUtil(
            mutableListOf(FilteredRealmDTO("1", "2019-02-22", "Iseseisvuspäev", "folk")),
            mutableListOf(FilteredRealmDTO("2", "2021-02-22", "Talvine madisepäev", "public"))
        )
    }

    @Test
    fun testGetOldListSize() {
        Truth.assertThat(
            holidayDiffUtil.oldListSize
        ).isEqualTo(1)
    }

    @Test
    fun testGetNewListSize() {
        Truth.assertThat(
            holidayDiffUtil.newListSize
        ).isEqualTo(1)
    }

    @Test
    fun areItemsTheSame() {
        Truth.assertThat(holidayDiffUtil.areItemsTheSame(0, 0)).isFalse()
    }

    @Test
    fun areContentsTheSame() {
        Truth.assertThat(holidayDiffUtil.areContentsTheSame(0, 0)).isFalse()
    }
}
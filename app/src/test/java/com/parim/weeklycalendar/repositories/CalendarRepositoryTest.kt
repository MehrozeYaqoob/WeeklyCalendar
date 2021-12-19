package com.parim.weeklycalendar.repositories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.model.RealmDTO
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.module.HolidayModule
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import retrofit2.Call

@RunWith(RobolectricTestRunner::class)
class CalendarRepositoryTest  {
    private lateinit var calendarRepository: CalendarRepository
    private lateinit var holidayModule: HolidayModule
    private lateinit var holidayDAO: HolidayDAO
    private lateinit var callbackRemoteDataFetched: IRepositoryCallback<RealmDTO>
    private lateinit var context: Context
    val requestDTO: RequestDTO = mock()

    @Before
     fun setUp() {
         context = ApplicationProvider.getApplicationContext()
         holidayModule = mock()
         holidayDAO = mock()
         callbackRemoteDataFetched = mock()
         calendarRepository = CalendarRepository(holidayModule,holidayDAO)
    }

    @Test
    fun testGetHolidays() {
//        calendarRepository.getHolidays(context,requestDTO,callbackRemoteDataFetched)
//        Mockito.`when`(holidayModule.getHolidays(requestDTO)).thenReturn(mock<Call<Any>>())
    }

    @Test
    fun testOnSaveRemoteData() {
        calendarRepository.onSaveRemoteData(context, listOf(),mock(),"2021-12-12")
    }

    @Test
    fun testOnRetrieveLocalData() {
        calendarRepository.onRetrieveLocalData(context,"2021-12-12",mock())
    }
}
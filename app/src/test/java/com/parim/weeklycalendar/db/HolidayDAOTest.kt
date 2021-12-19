package com.parim.weeklycalendar.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.RealmDTO
import io.realm.Realm
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HolidayDAOTest {
    private lateinit var holidayDAO: HolidayDAO
    private lateinit var context: Context
    private lateinit var mockRealm: Realm
    private lateinit var iRealmCallback: IRealmCallback<Boolean>
    private lateinit var callbackGetLocalData: IRepositoryCallback<FilteredRealmDTO>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        mockRealm = mock()
        holidayDAO = HolidayDAO(mockRealm)
        iRealmCallback = mock()
        callbackGetLocalData = mock()
    }

    @Test
    fun testOnSaveRemoteData_success() {
        holidayDAO.onSaveRemoteData(context, listOf(RealmDTO()), iRealmCallback, "2019-02-22")

    }

    @Test
    fun testOnRetrieveLocalData() {
        holidayDAO.onRetrieveLocalData(context, "2019-02-22", callbackGetLocalData)
    }
}
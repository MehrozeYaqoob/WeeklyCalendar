package com.parim.weeklycalendar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.RealmDTO
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.repositories.CalendarRepository

class CalendarViewModel(private val calendarRepository: CalendarRepository):ViewModel() {

    private val holidaysLiveData = MutableLiveData<List<FilteredRealmDTO>>()
    val holidayLiveData =  holidaysLiveData
    lateinit var callbackRemoteDataFetched: IRepositoryCallback<RealmDTO>

    init {
        onInitCallback()
        onRemoteDataFetched()
    }

    private fun onInitCallback(){
         callbackRemoteDataFetched = object : IRepositoryCallback<RealmDTO> {
            override fun onSuccess(body: List<RealmDTO>) {
                body.let { calendarRepository.onSaveRemoteData(it,callbackSaveRemoteData) }
            }
            override fun onFailure(message: String?) {

            }
        }
    }

    private fun onRemoteDataFetched(startDate: String  = "2019-02-01", endDate: String = "2019-02-28") {
        val requestDTO  = RequestDTO(BuildConfig.API_KEY, startDate = startDate, endDate = endDate)
        calendarRepository.getHolidays(requestDTO, callbackRemoteDataFetched)
    }

    val callbackSaveRemoteData = object : IRealmCallback<Boolean> {

        override fun onSuccess(success: Boolean) {
            when(success){
                true -> {
                    /*fetching from db*/
                    calendarRepository.onRetrieveLocalData(dateSelected = "2019-02-24", callbackRetrieveLocalData )
                }
            }
        }

        override fun onFailure(message: String?) {

        }
    }

    val callbackRetrieveLocalData = object : IRepositoryCallback<FilteredRealmDTO> {

        override fun onSuccess(body: List<FilteredRealmDTO>) {
            Log.e("assd",body.size.toString())
            holidayLiveData.postValue(body)
        }

        override fun onFailure(message: String?) {

        }
    }
}
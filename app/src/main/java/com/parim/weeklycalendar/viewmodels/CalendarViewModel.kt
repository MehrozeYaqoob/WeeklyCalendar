package com.parim.weeklycalendar.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.manager.PreferenceManager
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.RealmDTO
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.repositories.CalendarRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CalendarViewModel(private val calendarRepository: CalendarRepository): ViewModel() {

    private val holidaysLiveData = MutableLiveData<List<FilteredRealmDTO>>()
    val holidayLiveData =  holidaysLiveData

    private fun onRemoteDataFetched(context: Context,  startDate: String, endDate: String) {
        val requestDTO  = RequestDTO(BuildConfig.API_KEY, startDate = startDate, endDate = endDate)
        calendarRepository.getHolidays(context,requestDTO, callbackRemoteDataFetched)
    }


    @SuppressLint("SimpleDateFormat")
    fun onLoadData(context:  Context, date: Date) {  //"2019-02-24"
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd");

        val currentTime = System.currentTimeMillis()
        val lastFetchedTime  =  PreferenceManager.getLastUpdatedDate(context, currentTime )
        val diff = TimeUnit.DAYS.convert(currentTime - lastFetchedTime, TimeUnit.MILLISECONDS)

        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH,1)

        // This value 15 is configurable from remote config
        if(lastFetchedTime == currentTime || diff > 15){
            // fetch remote data
            onRemoteDataFetched(context =  context,  startDate = dateFormat.format(date), endDate = dateFormat.format(cal.time))
        }else{
            //fetch local data
            calendarRepository.onRetrieveLocalData(dateSelected = dateFormat.format(date), callbackRetrieveLocalData )
        }
    }

    private val callbackRemoteDataFetched = object : IRepositoryCallback<RealmDTO> {
        override fun onSuccess(context: Context?, body: List<RealmDTO>, date: String) {
            context?.let {  PreferenceManager.setLastUpdatedDate(it,System.currentTimeMillis()) }
            body.let { calendarRepository.onSaveRemoteData(it,callbackSaveRemoteData,date) }

        }
        override fun onFailure(message: String?) {

        }
    }

    val callbackSaveRemoteData = object : IRealmCallback<Boolean> {

        override fun onSuccess(success: Boolean, date: String) {
            when(success){
                true -> {
                    /*fetching from db*/
                    calendarRepository.onRetrieveLocalData(dateSelected = date, callbackRetrieveLocalData )
                }
            }
        }

        override fun onFailure(message: String?) {

        }
    }

    val callbackRetrieveLocalData = object : IRepositoryCallback<FilteredRealmDTO> {

        override fun onSuccess(context: Context?, body: List<FilteredRealmDTO>, date: String) {
            Log.e("assd",body.size.toString())
            holidayLiveData.postValue(body)
        }

        override fun onFailure(message: String?) {

        }
    }

}
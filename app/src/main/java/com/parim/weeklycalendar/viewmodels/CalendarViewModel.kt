package com.parim.weeklycalendar.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.R
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.manager.PreferenceManager
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.RealmDTO
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.repositories.CalendarRepository
import com.parim.weeklycalendar.sealed.ErrorEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CalendarViewModel(private val calendarRepository: CalendarRepository): ViewModel() {

    private val holidaysLiveData = MutableLiveData<List<FilteredRealmDTO>>()
    val holidayLiveData =  holidaysLiveData
    private val errorEventChannel = Channel<ErrorEvents>()
    val errorEventFlow = errorEventChannel.receiveAsFlow()

    // Step 0: Decide where to get data i.e: Remote or Local
    fun onLoadData(context:  Context, today: Date) {
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd",Locale.US)

        val currentTime = System.currentTimeMillis()
        val lastFetchedTime  =  PreferenceManager.getLastUpdatedDate(context, currentTime )
        val diff = TimeUnit.DAYS.convert(currentTime - lastFetchedTime, TimeUnit.MILLISECONDS)

        val cal = Calendar.getInstance()
        cal.time = today
        cal.add(Calendar.MONTH,1)

        // This value is configurable from remote config
        if(lastFetchedTime == currentTime || diff > PreferenceManager.getDaysAfterRemoteDataFetched(context, 10)){
            // fetch remote data
            onRemoteDataFetched(context =  context,  startDate = dateFormat.format(today), endDate = dateFormat.format(cal.time))
        }else{
            //fetch local data
            calendarRepository.onRetrieveLocalData(context, dateSelected = dateFormat.format(today), callbackRetrieveLocalData )
        }
    }

    // Step 1: Fetch data from server
    private fun onRemoteDataFetched(context: Context,  startDate: String, endDate: String) {
        val requestDTO  = RequestDTO(BuildConfig.API_KEY, startDate = startDate, endDate = endDate)
        calendarRepository.getHolidays(context,requestDTO, callbackRemoteDataFetched)
    }

    // Step 2: Save data to realm database
    private val callbackRemoteDataFetched = object : IRepositoryCallback<RealmDTO> {
        override fun onSuccess(context: Context?, body: List<RealmDTO>, date: String) {
            body.let { calendarRepository.onSaveRemoteData(context, it,callbackSaveRemoteData,date) }
        }
        override fun onFailure(context: Context?, message: String?) {
            triggerErrorEvent(ErrorEvents.ErrorFetchingRemoteData(context?.getString(R.string.error_fetching_remote_data) ?: ""))
        }
    }

    // Step 3: Fetch data from local database
    val callbackSaveRemoteData = object : IRealmCallback<Boolean> {
        override fun onSuccess(context: Context?, success: Boolean, date: String) {
            when(success){
                true -> {
                    context?.let {  PreferenceManager.setLastUpdatedDate(it,System.currentTimeMillis()) }
                    calendarRepository.onRetrieveLocalData(context,dateSelected = date, callbackRetrieveLocalData )
                }
            }
        }
        override fun onFailure(context: Context?, message: String?) {
            triggerErrorEvent(ErrorEvents.ErrorSavingRemoteData(context?.getString(R.string.error_saving_remote_data) ?: ""))
        }
    }

    // Step 4: Update live data object
    val callbackRetrieveLocalData = object : IRepositoryCallback<FilteredRealmDTO> {
        override fun onSuccess(context: Context?, body: List<FilteredRealmDTO>, date: String) {
            holidayLiveData.postValue(body)
        }
        override fun onFailure(context: Context?, message: String?) {
            triggerErrorEvent(ErrorEvents.ErrorRetrievingLocalData(context?.getString(R.string.error_retrieving_local_data) ?: ""))
        }
    }

    // Step 5: Error handling if needed
    fun triggerErrorEvent(errorEvents: ErrorEvents) = viewModelScope.launch {
        errorEventChannel.send(errorEvents)
    }
}
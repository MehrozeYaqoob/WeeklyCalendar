package com.parim.weeklycalendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.model.Resource
import com.parim.weeklycalendar.model.ResponseDTO
import com.parim.weeklycalendar.repositories.CalendarRepository
import io.reactivex.disposables.CompositeDisposable

class CalendarViewModel(private val calendarRepository: CalendarRepository):ViewModel() {

    private val holidays = MutableLiveData<Resource<ResponseDTO>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchHolidays()
    }

    private fun fetchHolidays() {
        holidays.postValue(Resource.loading(null))
        val requestDTO  = RequestDTO(BuildConfig.API_KEY, startDate = "2019-02-01", endDate = "2019-02-28")
        calendarRepository.getHolidays(requestDTO, callbackHolidayList)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getHolidays(): LiveData<Resource<ResponseDTO>> {
        return holidays
    }

    private val callbackHolidayList = object : IRepositoryCallback<Holiday> {

        override fun onSuccess(body: Holiday?) {
            TODO("Not yet implemented")
        }

        override fun onFailure(message: String?) {

        }

        override fun onError(errorResponse: String?) {

        }

        override fun onRequestTimeOut() {
        }

    }
}
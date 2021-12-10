package com.parim.weeklycalendar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.model.Resource
import com.parim.weeklycalendar.model.ResponseDTO
import com.parim.weeklycalendar.repositories.CalendarRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CalendarViewModel(private val calendarRepository: CalendarRepository):ViewModel() {

    private val holidays = MutableLiveData<Resource<ResponseDTO>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchHolidays()
    }

    private fun fetchHolidays() {
        holidays.postValue(Resource.loading(null))
        compositeDisposable.add(
            calendarRepository.getHolidays(RequestDTO(BuildConfig.API_KEY, startDate = "2019-02-01", endDate = "2019-02-28"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ holidayList ->
                    Log.d("Holidays", "fetchHolidays: "+holidayList)
                    holidays.postValue(Resource.success(holidayList))
                }, { throwable ->
                    holidays.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getHolidays(): LiveData<Resource<ResponseDTO>> {
        return holidays
    }
}
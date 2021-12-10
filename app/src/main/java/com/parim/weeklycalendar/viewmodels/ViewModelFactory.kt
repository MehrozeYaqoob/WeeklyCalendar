package com.parim.weeklycalendar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parim.weeklycalendar.module.HolidayModule
import com.parim.weeklycalendar.repositories.CalendarRepository

class ViewModelFactory(private val holidayModule: HolidayModule) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(CalendarRepository(holidayModule)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
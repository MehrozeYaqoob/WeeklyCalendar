package com.parim.weeklycalendar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.model.RealmDTO
import com.parim.weeklycalendar.module.HolidayModule
import com.parim.weeklycalendar.repositories.CalendarRepository

class ViewModelFactory(private val holidayModule: HolidayModule, private val holidayDAO: HolidayDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(CalendarRepository(holidayModule, holidayDAO)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
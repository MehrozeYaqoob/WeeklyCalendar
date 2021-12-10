package com.parim.weeklycalendar.repositories

import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.module.HolidayModule


class CalendarRepository(private val holidayModule: HolidayModule) {
    fun getHolidays(requestDTO: RequestDTO) = holidayModule.getHolidays(requestDTO)
}
package com.parim.weeklycalendar.module

import com.parim.weeklycalendar.contracts.IHolidayService
import com.parim.weeklycalendar.model.RequestDTO

class HolidayModule(private val holidayService: IHolidayService) {
    fun getHolidays(requestDTO: RequestDTO) = holidayService.getHolidays(requestDTO)
}
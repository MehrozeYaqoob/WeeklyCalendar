package com.parim.weeklycalendar.contracts

import com.parim.weeklycalendar.model.RequestDTO
import retrofit2.Call

interface IHolidayService {
    fun getHolidays(requestDTO: RequestDTO): Call<Any>
}
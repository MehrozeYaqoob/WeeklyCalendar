package com.parim.weeklycalendar.contracts

import com.parim.weeklycalendar.model.RequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IHolidayAPI {
    @POST("/api/holidays")
    fun getHolidays(@Body requestDTO: RequestDTO): Call<Any>
}

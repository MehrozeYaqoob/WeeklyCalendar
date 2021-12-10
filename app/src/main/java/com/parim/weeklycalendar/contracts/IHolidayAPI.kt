package com.parim.weeklycalendar.contracts

import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.model.ResponseDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IHolidayAPI {
    @POST("/api/holidays")
    fun getHolidays(@Body requestDTO: RequestDTO): Single<ResponseDTO>
}

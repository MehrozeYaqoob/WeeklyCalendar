package com.parim.weeklycalendar.contracts

import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.model.ResponseDTO
import io.reactivex.Single
import retrofit2.http.GET

interface IHolidayService {
    fun getHolidays(requestDTO: RequestDTO): Single<ResponseDTO>
}
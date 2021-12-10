package com.parim.weeklycalendar.provider

import com.parim.weeklycalendar.contracts.IHolidayAPI
import com.parim.weeklycalendar.contracts.IHolidayService
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.model.ResponseDTO
import io.reactivex.Single

class HolidayServiceProvider(private val api: IHolidayAPI = RetrofitProvider.createAPI(
    IHolidayAPI::class.java)) : IHolidayService {
    override fun getHolidays(requestDTO: RequestDTO): Single<ResponseDTO> {
        return api.getHolidays(requestDTO)
    }
}
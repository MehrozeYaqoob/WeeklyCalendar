package com.parim.weeklycalendar.provider

import RetrofitProvider
import com.parim.weeklycalendar.contracts.IHolidayAPI
import com.parim.weeklycalendar.contracts.IHolidayService
import com.parim.weeklycalendar.model.RequestDTO
import retrofit2.Call

class HolidayServiceProvider(private val api: IHolidayAPI = RetrofitProvider.createAPI(
    IHolidayAPI::class.java)) : IHolidayService {
    override fun getHolidays(requestDTO: RequestDTO): Call<Any> {
        return api.getHolidays(requestDTO)
    }
}

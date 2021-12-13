package com.parim.weeklycalendar.provider
import com.parim.weeklycalendar.contracts.IHolidayAPI
import com.parim.weeklycalendar.contracts.IHolidayService
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.networking.RetrofitProvider
import retrofit2.Call

class HolidayServiceProvider(private val api: IHolidayAPI = RetrofitProvider.createAPI(
    IHolidayAPI::class.java)) : IHolidayService {
    override fun getHolidays(requestDTO: RequestDTO): Call<Any> {
        return api.getHolidays(requestDTO)
    }
}

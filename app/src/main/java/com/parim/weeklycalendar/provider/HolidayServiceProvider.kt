package com.parim.weeklycalendar.provider

import android.util.Log
import com.parim.weeklycalendar.contracts.IHolidayAPI
import com.parim.weeklycalendar.contracts.IHolidayService
import com.parim.weeklycalendar.model.RequestDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson

class HolidayServiceProvider(private val api: IHolidayAPI = RetrofitProvider.createAPI(
    IHolidayAPI::class.java)) : IHolidayService {
    override fun getHolidays(requestDTO: RequestDTO): Call<Object> {
        return api.getHolidays(requestDTO)
//        callback.enqueue(object : Callback<Object> {
//            override fun onFailure(call: Call<Object>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onResponse(call: Call<Object>, response: Response<Object>) {
//                Log.e("TAG", "response 33: " + Gson().toJson(response.body()))
//            }
//        })
    }
}

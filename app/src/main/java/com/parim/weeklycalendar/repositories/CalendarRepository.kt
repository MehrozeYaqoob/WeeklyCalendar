package com.parim.weeklycalendar.repositories

import android.util.Log
import com.google.gson.Gson
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.module.HolidayModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken
import com.parim.weeklycalendar.model.EEHolidays
import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.ResponseDTO
import org.json.JSONObject
import java.util.*


class CalendarRepository(private val holidayModule: HolidayModule) {
    fun getHolidays(requestDTO: RequestDTO, callbackHolidays: IRepositoryCallback<Holiday>) {
        val callback = holidayModule.getHolidays(requestDTO)
                callback.enqueue(object : Callback<Object> {

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                Log.e("TAG", "response 33: " + Gson().toJson(response.body()))

                val data: JSONObject = JSONObject(Gson().toJson(response.body()))
                val error = data.getBoolean("error")
                val holiday =  data.getJSONObject("holidays")

                val holidayMapType = object : TypeToken<HashMap<String, List<EEHolidays>>>() {}.type
                val  mapType = Gson().fromJson<HashMap<String, List<EEHolidays>>>(holiday.toString(),holidayMapType)

                Log.e("Repo", mapType.toString())
                callbackHolidays.onSuccess(Holiday(mapType))
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}
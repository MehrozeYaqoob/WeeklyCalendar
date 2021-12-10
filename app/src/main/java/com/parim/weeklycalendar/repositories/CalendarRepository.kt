package com.parim.weeklycalendar.repositories

import com.google.gson.Gson
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.RequestDTO
import com.parim.weeklycalendar.module.HolidayModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.model.EEHolidays
import com.parim.weeklycalendar.model.Holiday
import org.json.JSONObject
import java.util.*


class CalendarRepository(private val holidayModule: HolidayModule, private val holidayDAO: HolidayDAO) {
    fun getHolidays(requestDTO: RequestDTO, callbackHolidays: IRepositoryCallback<Holiday>?) {
        val callback = holidayModule.getHolidays(requestDTO)

        callback.enqueue(object : Callback<Object> {
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                val data = JSONObject(Gson().toJson(response.body()))
                val error = data.getBoolean("error")
                val holidayJSONObject =  data.getJSONObject("holidays")

                val holidayMapType = object : TypeToken<HashMap<String, List<EEHolidays>>>() {}.type
                val  mapType = Gson().fromJson<HashMap<String, List<EEHolidays>>>(holidayJSONObject.toString(),holidayMapType)
                val holidays =  Holiday(mapType)
                callbackHolidays?.onSuccess(holidays)
                holidayDAO.addHoliday(holidays)
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
                callbackHolidays?.onFailure(t.localizedMessage)
            }
        })
    }

}
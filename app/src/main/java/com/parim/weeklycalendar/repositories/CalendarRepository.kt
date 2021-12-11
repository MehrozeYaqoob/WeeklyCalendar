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
import com.parim.weeklycalendar.model.RealmDTO
import org.json.JSONObject
import java.util.*


class CalendarRepository(private val holidayModule: HolidayModule, private val holidayDAO: HolidayDAO) {

    fun getHolidays(requestDTO: RequestDTO, callbackRemoteDataFetched: IRepositoryCallback<Holiday>?) {
        val callback = holidayModule.getHolidays(requestDTO)

        callback.enqueue(object : Callback<Object> {
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                val data = JSONObject(Gson().toJson(response.body()))
                val error = data.getBoolean("error")
                val holidayJSONObject =  data.getJSONObject("holidays")

                val holidayMapType = object : TypeToken<HashMap<String, List<EEHolidays>>>() {}.type
                val  mapType = Gson().fromJson<HashMap<String, List<EEHolidays>>>(holidayJSONObject.toString(),holidayMapType)
                val holidays =  Holiday(mapType)
                when(error){
                    false  ->  callbackRemoteDataFetched?.onSuccess(holidays)
                    else ->  callbackRemoteDataFetched?.onFailure(data.getString("reason" ?: ""))
                }
            }
            override fun onFailure(call: Call<Object>, t: Throwable) {
                callbackRemoteDataFetched?.onFailure(t.localizedMessage)
            }
        })
    }

    fun onSaveRemoteData(holidays: Holiday, callbackSaveRemoteData: IRepositoryCallback<Boolean>){
        holidayDAO.onSaveRemoteData(holidays,callbackSaveRemoteData)
    }

    fun onRetrieveLocalData(dateSelected: String, callbackGetLocalData: IRepositoryCallback<List<RealmDTO>>){
        holidayDAO.onRetrieveLocalData(dateSelected,callbackGetLocalData)
    }

}
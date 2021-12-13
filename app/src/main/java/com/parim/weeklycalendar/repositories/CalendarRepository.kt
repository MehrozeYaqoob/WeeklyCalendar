package com.parim.weeklycalendar.repositories

import android.content.Context
import com.google.gson.Gson
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.module.HolidayModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.reflect.TypeToken
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.model.*
import com.parim.weeklycalendar.utils.Constants.JSON_ATTRIBUTE_ERROR
import com.parim.weeklycalendar.utils.Constants.JSON_ATTRIBUTE_HOLIDAYS
import com.parim.weeklycalendar.utils.Constants.JSON_ATTRIBUTE_REASON
import org.json.JSONObject
import java.util.*

class CalendarRepository(private val holidayModule: HolidayModule, private val holidayDAO: HolidayDAO) {

    fun getHolidays(context: Context, requestDTO: RequestDTO, callbackRemoteDataFetched: IRepositoryCallback<RealmDTO>?) {
        val callback = holidayModule.getHolidays(requestDTO)

        callback.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val data = JSONObject(Gson().toJson(response.body()))
                val error = data.getBoolean(JSON_ATTRIBUTE_ERROR)
                val holidayJSONObject =  data.getJSONObject(JSON_ATTRIBUTE_HOLIDAYS)

                val holidayMapType = object : TypeToken<HashMap<String, List<EEHolidays>>>() {}.type
                val  mapType = Gson().fromJson<HashMap<String, List<EEHolidays>>>(holidayJSONObject.toString(),holidayMapType)
                val holidays =  Holiday(mapType)
                when(error){
                    false  ->  callbackRemoteDataFetched?.onSuccess(context,holidays.getFlatHolidayData(), requestDTO.startDate)
                    else ->  callbackRemoteDataFetched?.onFailure(context, data.getString(JSON_ATTRIBUTE_REASON))
                }
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                callbackRemoteDataFetched?.onFailure(context, t.localizedMessage)
            }
        })
    }

    fun onSaveRemoteData(context: Context?, holidays: List<RealmDTO>, callbackSaveRemoteData: IRealmCallback<Boolean>, dateSelected: String){
        holidayDAO.onSaveRemoteData(context, holidays,callbackSaveRemoteData,dateSelected)
    }

    fun onRetrieveLocalData(context: Context?, dateSelected: String, callbackGetLocalData: IRepositoryCallback<FilteredRealmDTO>){
        holidayDAO.onRetrieveLocalData(context, dateSelected,callbackGetLocalData)
    }

}
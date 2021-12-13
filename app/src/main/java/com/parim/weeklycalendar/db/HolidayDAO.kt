package com.parim.weeklycalendar.db

import android.content.Context
import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.RealmDTO
import io.realm.Realm

class HolidayDAO(private val realm: Realm = Realm.getDefaultInstance()) {

    /* * Write Operation */
    fun onSaveRemoteData(context: Context?, holidays: List<RealmDTO>, callbackSaveRemoteData: IRealmCallback<Boolean>, dateSelected: String){
        realm.executeTransactionAsync ({
            when { holidays.isNotEmpty() -> it.deleteAll() }
            it.insertOrUpdate(holidays)
        }, {
            callbackSaveRemoteData.onSuccess(context,true,dateSelected)
        }, {
            callbackSaveRemoteData.onFailure(context,it.localizedMessage)
        })
    }

    /* * Read Operation */
    fun onRetrieveLocalData(context: Context?,dateSelected: String, callbackGetLocalData: IRepositoryCallback<FilteredRealmDTO>){
        realm.executeTransactionAsync {
            val data =
                it.where(RealmDTO::class.java).equalTo("date", dateSelected).findAll().map {realmDTO->
                    FilteredRealmDTO(id = realmDTO.id, name = realmDTO.name, date = realmDTO.date, type = realmDTO.type)
                }

            callbackGetLocalData.onSuccess(context, data, dateSelected)
        }
    }
}
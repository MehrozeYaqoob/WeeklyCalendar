package com.parim.weeklycalendar.db

import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.RealmDTO
import io.realm.Realm

class HolidayDAO(private val realm: Realm = Realm.getDefaultInstance()) {

    fun onSaveRemoteData(holiday: Holiday, callbackSaveRemoteData: IRepositoryCallback<Boolean>){
        val list = holiday.getFlatHolidayData()
        realm.executeTransactionAsync ({
            when { list.size > 0  -> it.deleteAll() }
            it.copyToRealmOrUpdate(list) /*This is slower than insertOrUpdate*/
//            it.insertOrUpdate(list)
        },Realm.Transaction.OnSuccess {
            callbackSaveRemoteData.onSuccess(true)
        })
    }

    fun onRetrieveLocalData(dateSelected: String, callbackGetLocalData: IRepositoryCallback<List<RealmDTO>>){
        realm.executeTransactionAsync {
            val data =  it.where(RealmDTO::class.java).equalTo("date",dateSelected).findAll()
            callbackGetLocalData.onSuccess(data)
        }
    }
}
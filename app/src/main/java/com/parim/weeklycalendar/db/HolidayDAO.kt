package com.parim.weeklycalendar.db

import com.parim.weeklycalendar.contracts.IRealmCallback
import com.parim.weeklycalendar.contracts.IRepositoryCallback
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.RealmDTO
import io.realm.Realm

class HolidayDAO(private val realm: Realm = Realm.getDefaultInstance()) {

    fun onSaveRemoteData(holidays: List<RealmDTO>, callbackSaveRemoteData: IRealmCallback<Boolean>,dateSelected: String){
        realm.executeTransactionAsync ({
            when { holidays.isNotEmpty() -> it.deleteAll() }
//            it.copyToRealmOrUpdate(list) /*This is slower than insertOrUpdate*/
            it.insertOrUpdate(holidays)
        },Realm.Transaction.OnSuccess {
            callbackSaveRemoteData.onSuccess(true,dateSelected)
        })
    }

    fun onRetrieveLocalData(dateSelected: String, callbackGetLocalData: IRepositoryCallback<FilteredRealmDTO>){
        val date = dateSelected
        realm.executeTransactionAsync { realm1 ->
            val data =  realm1.where(RealmDTO::class.java).equalTo("date",dateSelected).findAll().map {
                FilteredRealmDTO(id = it.id, name = it.name, date = it.date, type = it.type  ) }

            callbackGetLocalData.onSuccess(null, data, dateSelected)
        }
    }
}
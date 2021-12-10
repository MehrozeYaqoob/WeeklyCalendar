package com.parim.weeklycalendar.db

import com.parim.weeklycalendar.model.Holiday
import com.parim.weeklycalendar.model.RealmDTO
import io.realm.Realm

class HolidayDAO(private val realm: Realm = Realm.getDefaultInstance()) {

    fun addHoliday(holidays: Holiday){
        val list = mutableListOf<RealmDTO>()
        realm.executeTransactionAsync {
            holidays.hashMap.forEach { date ->
                date.value.forEach {
                    val realmObject =  RealmDTO(date = date.key )
                    realmObject.name  =  it.name
                    realmObject.type =  it.type
                    list.add(realmObject)
                }
            }
            when { list.size > 0  -> it.deleteAll() }
            it.copyToRealmOrUpdate(list)
        }
    }

    fun getHolidays(startDate: String, endDate: String){
        
    }
}
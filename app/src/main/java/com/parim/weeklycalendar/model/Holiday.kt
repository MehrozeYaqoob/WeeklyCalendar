package com.parim.weeklycalendar.model

data class Holiday(val hashMap: HashMap<String, List<EEHolidays>>){

    fun getFlatHolidayData(): List<RealmDTO> {
        val list = mutableListOf<RealmDTO>()
        hashMap.forEach { date ->
            date.value.forEach {
                val realmObject =  RealmDTO(date = date.key )
                realmObject.name  =  it.name
                realmObject.type =  it.type
                list.add(realmObject)
            }
        }
        return list
    }
}

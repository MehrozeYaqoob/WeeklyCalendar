package com.parim.weeklycalendar.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class FilteredRealmDTO(var id: String, var date: String = "", var name: String = "", var type: String = "")
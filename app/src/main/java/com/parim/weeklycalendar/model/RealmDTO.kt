package com.parim.weeklycalendar.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class RealmDTO(@PrimaryKey
                     var id: String = ObjectId().toHexString(), var date: String = "", var name: String = "", var type: String = ""): RealmObject()
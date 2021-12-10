package com.parim.weeklycalendar.views

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CalendarApplication: Application() {
        override fun onCreate() {
            super.onCreate()
            Realm.init(this)
            val config = RealmConfiguration.Builder()
                .name("estonian_calender.realm")
                /** since read and writes are 10% slower on encrypted db so it depends on security of  data from case to case*/
//            .encryptionKey(getKey())
                .build()
            Realm.setDefaultConfiguration(config)
        }

}
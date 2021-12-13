package com.parim.weeklycalendar.contracts

import android.content.Context
import android.util.Log
import com.parim.weeklycalendar.model.FilteredRealmDTO

interface IRepositoryCallback<T> {
    fun onSuccess(context: Context?, body: List<T>, date: String)
    fun onFailure(message: String?) { Log.i("","")}
}

interface IRealmCallback<T> {
    fun onSuccess(success: T, date: String)
    fun onFailure(message: String?) { Log.i("","")}
}
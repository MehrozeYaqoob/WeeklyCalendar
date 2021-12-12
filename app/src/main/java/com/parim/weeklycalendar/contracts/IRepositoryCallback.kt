package com.parim.weeklycalendar.contracts

import android.util.Log
import com.parim.weeklycalendar.model.FilteredRealmDTO

interface IRepositoryCallback<T> {
    fun onSuccess(body: List<T>)
    fun onFailure(message: String?) { Log.i("","")}
}

interface IRealmCallback<T> {
    fun onSuccess(success: T)
    fun onFailure(message: String?) { Log.i("","")}
}
package com.parim.weeklycalendar.contracts

import android.content.Context
import android.util.Log

interface IRealmCallback<T> {
    fun onSuccess(context: Context?, success: T, date: String)
    fun onFailure(context: Context?,message: String?) { Log.i("","")}
}
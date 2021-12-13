package com.parim.weeklycalendar.contracts

import android.content.Context
import android.util.Log

interface IRepositoryCallback<T> {
    fun onSuccess(context: Context?, body: List<T>, date: String)
    fun onFailure(context: Context?, message: String?) { Log.i("","")}
}

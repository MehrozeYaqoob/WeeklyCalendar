package com.parim.weeklycalendar.contracts

import android.util.Log

interface IRepositoryCallback<T> {
    fun onSuccess(body: T?)
    fun onFailure(message: String?) { Log.i("","")}
}
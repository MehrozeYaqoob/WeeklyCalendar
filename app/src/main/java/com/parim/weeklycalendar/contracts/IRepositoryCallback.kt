package com.parim.weeklycalendar.contracts

import android.content.Context

interface IRepositoryCallback<T> {
    fun onSuccess(context: Context?, body: List<T>, date: String)
    fun onFailure(context: Context?, message: String?)
}

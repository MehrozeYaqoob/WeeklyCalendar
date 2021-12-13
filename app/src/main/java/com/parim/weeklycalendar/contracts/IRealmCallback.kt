package com.parim.weeklycalendar.contracts

import android.content.Context

interface IRealmCallback<T> {
    fun onSuccess(context: Context?, success: T, date: String)
    fun onFailure(context: Context?,message: String?)
}
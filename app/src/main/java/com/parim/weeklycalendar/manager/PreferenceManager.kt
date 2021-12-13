package com.parim.weeklycalendar.manager

import android.content.Context


object PreferenceManager {
    private const val PREFS_NAME = "parim_key"
    private const val TIME_WHEN_REMOTE_DATA_FETCHED = "lastDataFetchedTime"

    fun setLastUpdatedDate(context: Context, currentDateInMillies: Long) {
        setPreferences(context, TIME_WHEN_REMOTE_DATA_FETCHED, currentDateInMillies)
    }

    fun getLastUpdatedDate(context: Context, defaultValues: Long): Long {
        return getPreferences(context, TIME_WHEN_REMOTE_DATA_FETCHED, defaultValues)
    }

    @Synchronized
    fun setPreferences(ctx: Context, key: String?, value: Long) {
        val preferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    private fun getPreferences(ctx: Context, key: String?, defaultValue: Long): Long {
        val preferences = ctx.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
        return preferences.getLong(key, defaultValue)
    }


}
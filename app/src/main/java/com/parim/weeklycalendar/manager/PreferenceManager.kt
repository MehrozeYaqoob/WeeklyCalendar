package com.parim.weeklycalendar.manager

import android.content.Context

object PreferenceManager {
    private const val PREFS_NAME = "calendar_key"
    private const val TIME_WHEN_REMOTE_DATA_FETCHED = "lastDataFetchedTime"
    private const val DAYS_AFTER_REMOTE_DATA_FETCHED = "daysAfterRemoteDataFetched"

    fun setLastUpdatedDate(context: Context, currentDateInMillis: Long) {
        setPreferences(context, TIME_WHEN_REMOTE_DATA_FETCHED, currentDateInMillis)
    }

    fun getLastUpdatedDate(context: Context, defaultValues: Long): Long {
        return getPreferences(context, TIME_WHEN_REMOTE_DATA_FETCHED, defaultValues)
    }

    fun setDaysAfterRemoteDataFetched(context: Context, refreshTime: Long) {
        setPreferences(context, DAYS_AFTER_REMOTE_DATA_FETCHED, refreshTime)
    }

    fun getDaysAfterRemoteDataFetched(context: Context, defaultValues: Long): Long {
        return getPreferences(context, DAYS_AFTER_REMOTE_DATA_FETCHED, defaultValues)
    }

    @Synchronized
    fun setPreferences(ctx: Context, key: String?, value: Long) {
        val preferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply() // we can also use commit which applies immediately
    }

    private fun getPreferences(ctx: Context, key: String?, defaultValue: Long): Long {
        val preferences = ctx.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )
        return preferences.getLong(key, defaultValue)
    }


}
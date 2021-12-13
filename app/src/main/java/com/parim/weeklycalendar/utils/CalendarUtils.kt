package com.parim.weeklycalendar.utils

import java.text.SimpleDateFormat
import java.util.*

class CalendarUtils {

    companion object {
        @JvmStatic
        val DB_DATE_FORMAT = "yyyyMMdd"
        @JvmStatic
        val DISPLAY_WEEK_DAY_FORMAT = "EEEEEE"
        @JvmStatic
        val DISPLAY_MONTH_FORMAT = "MMMM"
        @JvmStatic
        val DISPLAY_DATE_FORMAT = "dd"

        /**
         * Returns Date Object from date string and date format
         *
         * @param locale your preferred locale
         * @param date string date
         * @param format string date format
         * @return IF OK date object ELSE null
         */
        @JvmStatic
        fun dateStringFromFormat(locale: Locale = Locale.getDefault(), date: Date, format: String): String? {
            return try {
                val formatter =
                    SimpleDateFormat(format, locale)
                formatter.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * @param date date to be converted
         * @return String of date
         */
        @JvmStatic
        fun getGmt(date: Date): String {
            val dfDate = SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault())
            return dfDate.format(date.time)
        }
    }
}
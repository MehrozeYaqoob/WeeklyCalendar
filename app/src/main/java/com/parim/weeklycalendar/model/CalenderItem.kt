package com.parim.weeklycalendar.model

import com.parim.weeklycalendar.utils.CalendarUtils
import java.util.*

class CalenderItem constructor(
    var date: Date,
    // Span size of cell for grid view
    var spanSize: Int,
    // For offset of new month
    var isEmpty: Boolean,
    // Header is simply a Month name cell
    var isHeader: Boolean
) {
    override fun toString(): String {
        return String.format(
            Locale.getDefault(),
            "date: %s, spanSize: %d, isEmpty: %s, isHeader: %s",
            CalendarUtils.getGmt(date),
            spanSize,
            isEmpty.toString(),
            isHeader.toString()
        )
    }
}
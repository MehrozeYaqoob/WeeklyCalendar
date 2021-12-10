package com.parim.weeklycalendar.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R

class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDay)
    val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDate)
}
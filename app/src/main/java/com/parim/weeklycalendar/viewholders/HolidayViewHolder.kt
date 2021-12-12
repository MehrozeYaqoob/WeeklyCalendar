package com.parim.weeklycalendar.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R

class HolidayViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewHoliday: TextView = itemView.findViewById(R.id.holidayName)
}

package com.parim.weeklycalendar.viewholders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R

class HolidayViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewHoliday: TextView = itemView.findViewById(R.id.holidayName)
    var cardHoliday: CardView = itemView.findViewById(R.id.holidayCard)
}

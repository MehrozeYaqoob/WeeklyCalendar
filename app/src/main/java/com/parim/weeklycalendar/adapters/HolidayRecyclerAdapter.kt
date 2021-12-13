package com.parim.weeklycalendar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.viewholders.HolidayViewHolder


class HolidayRecyclerAdapter(private val holidayList: MutableList<FilteredRealmDTO>): RecyclerView.Adapter<HolidayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holiday_vertical, parent, false)
        return HolidayViewHolder(view)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        holder.textViewHoliday.text = holidayList[position].name
        if(holidayList[position].type  == "public"){
            holder.cardHoliday.setCardBackgroundColor(Color.parseColor("#FAFAFA"));
        }
    }

    override fun getItemCount(): Int {
        return holidayList.size
    }

    fun onAddHolidayData(list:  List<FilteredRealmDTO>){
        holidayList.clear()
        holidayList.addAll(list)
        // We can use diff utils here but since list wont contain to  much data in this case so going with dataSetChange method
        notifyDataSetChanged()
    }
}
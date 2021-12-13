package com.parim.weeklycalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R
import com.parim.weeklycalendar.diffutils.HolidayDiffUtil
import com.parim.weeklycalendar.model.FilteredRealmDTO
import com.parim.weeklycalendar.viewholders.HolidayViewHolder


class HolidayRecyclerAdapter(var context: Context, private val holidayList: MutableList<FilteredRealmDTO>): RecyclerView.Adapter<HolidayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holiday_vertical, parent, false)
        return HolidayViewHolder(view)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        holder.textViewHoliday.text = holidayList[position].name
        if(holidayList[position].type  == context.getString(R.string.holiday_type)){
            holder.cardHoliday.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey_50))
        }
    }

    override fun getItemCount(): Int {
        return holidayList.size
    }

    fun onAddHolidayData(list:  List<FilteredRealmDTO>){
        val diffCallback = HolidayDiffUtil(this.holidayList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.holidayList.clear()
        this.holidayList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}
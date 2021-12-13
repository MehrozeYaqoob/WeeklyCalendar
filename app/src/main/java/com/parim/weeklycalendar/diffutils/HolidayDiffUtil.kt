package com.parim.weeklycalendar.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.parim.weeklycalendar.model.FilteredRealmDTO

class HolidayDiffUtil(private var oldHolidayList: MutableList<FilteredRealmDTO>, private var newHolidayList: List<FilteredRealmDTO>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldHolidayList.size
    }

    override fun getNewListSize(): Int {
        return newHolidayList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHolidayList[oldItemPosition].id == newHolidayList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHoliday = oldHolidayList[oldItemPosition]
        val newHoliday = newHolidayList[newItemPosition]
        return oldHoliday.name == newHoliday.name
    }

}
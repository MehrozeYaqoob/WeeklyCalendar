package com.parim.weeklycalendar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.R
import com.parim.weeklycalendar.utils.RecyclerCalendarConfiguration
import com.parim.weeklycalendar.model.CalenderItem
import com.parim.weeklycalendar.contracts.IDateSelected
import com.parim.weeklycalendar.utils.CalendarUtils
import com.parim.weeklycalendar.viewholders.MonthCalendarViewHolder
import java.util.*

class HorizontalRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    private val configuration: RecyclerCalendarConfiguration,
    private var selectedDate: Date,
    private val dateSelectListener: IDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_horizontal, parent, false)
        return MonthCalendarViewHolder(
            view
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: CalenderItem
    ) {
        val monthViewHolder: MonthCalendarViewHolder = holder as MonthCalendarViewHolder
        val context: Context = monthViewHolder.itemView.context
        monthViewHolder.itemView.visibility = View.VISIBLE

        monthViewHolder.itemView.setOnClickListener(null)

        monthViewHolder.itemView.background = null
        monthViewHolder.textViewDay.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )
        monthViewHolder.textViewDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack
            )
        )

        when {
            calendarItem.isHeader -> {
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.time = calendarItem.date

                val month: String = CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = selectedCalendar.time,
                    format = CalendarUtils.DISPLAY_MONTH_FORMAT
                ) ?: ""
                val year = selectedCalendar[Calendar.YEAR].toLong()

                monthViewHolder.textViewDay.text = year.toString()
                monthViewHolder.textViewDate.text = month

                monthViewHolder.itemView.setOnClickListener(null)
            }
            calendarItem.isEmpty -> {
                monthViewHolder.itemView.visibility = View.GONE
                monthViewHolder.textViewDay.text = ""
                monthViewHolder.textViewDate.text = ""
            }
            else -> {
                val calendarDate = Calendar.getInstance()
                calendarDate.time = calendarItem.date

                val stringCalendarTimeFormat: String =
                    CalendarUtils.dateStringFromFormat(
                        locale = configuration.calendarLocale,
                        date = calendarItem.date,
                        format = CalendarUtils.DB_DATE_FORMAT
                    )
                        ?: ""
                val stringSelectedTimeFormat: String =
                    CalendarUtils.dateStringFromFormat(
                        locale = configuration.calendarLocale,
                        date = selectedDate,
                        format = CalendarUtils.DB_DATE_FORMAT
                    ) ?: ""

                if (stringCalendarTimeFormat == stringSelectedTimeFormat) {
                    monthViewHolder.itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.layout_round_corner_filled)
                    monthViewHolder.textViewDay.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorWhite
                        )
                    )
                    monthViewHolder.textViewDate.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorWhite
                        )
                    )
                }

                val day: String = CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DISPLAY_WEEK_DAY_FORMAT
                ) ?: ""

                monthViewHolder.textViewDay.text = day

                monthViewHolder.textViewDate.text =
                    CalendarUtils.dateStringFromFormat(
                        locale = configuration.calendarLocale,
                        date = calendarDate.time,
                        format = CalendarUtils.DISPLAY_DATE_FORMAT
                    ) ?: ""

                monthViewHolder.itemView.setOnClickListener {
                    selectedDate = calendarItem.date
                    dateSelectListener.run { onDateSelected(calendarItem.date) }
                    notifyDataSetChanged()
                }
            }
        }
    }
}
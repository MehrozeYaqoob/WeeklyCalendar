package com.parim.weeklycalendar.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.PagerSnapHelper
import com.parim.weeklycalendar.utils.CalendarUtils
import com.parim.weeklycalendar.adapters.HorizontalRecyclerCalendarAdapter
import com.parim.weeklycalendar.contracts.IDateSelected
import com.parim.weeklycalendar.utils.RecyclerCalendarConfiguration
import com.parim.weeklycalendar.databinding.ActivityMainBinding
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.module.HolidayModule
import com.parim.weeklycalendar.provider.HolidayServiceProvider
import com.parim.weeklycalendar.viewmodels.CalendarViewModel
import com.parim.weeklycalendar.viewmodels.ViewModelFactory
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var date: Date
    private lateinit var startCal: Calendar
    private lateinit var endCal: Calendar
    private lateinit var recyclerViewConfiguration: RecyclerCalendarConfiguration
    private lateinit var calendarAdapterHorizontal:HorizontalRecyclerCalendarAdapter
    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onInitUI()
        onProvideViewModel()
        onConfigureRecyclerView()
        onLoadRecyclerAdapter()
        onObserveLiveData()
        onAttachPageSnap()

        binding.textViewSelectedDate.text =
            CalendarUtils.dateStringFromFormat(
                locale = recyclerViewConfiguration.calendarLocale,
                date = date,
                format = CalendarUtils.LONG_DATE_FORMAT
            ) ?: ""
    }

    private fun onInitUI() {
        date = Date()
        date.time = System.currentTimeMillis()

        startCal = Calendar.getInstance()

        endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)
    }

    private fun onProvideViewModel() {
        calendarViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(HolidayModule(HolidayServiceProvider()), HolidayDAO())
        ).get(CalendarViewModel::class.java)
    }

    private fun onConfigureRecyclerView() {
        recyclerViewConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )
        recyclerViewConfiguration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

    }

    private fun onLoadRecyclerAdapter(){
        calendarAdapterHorizontal =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = recyclerViewConfiguration,
                selectedDate = date,
                dateSelectListener = object : IDateSelected {
                    override fun onDateSelected(date: Date) {
                        binding.textViewSelectedDate.text =
                            CalendarUtils.dateStringFromFormat(
                                locale = recyclerViewConfiguration.calendarLocale,
                                date = date,
                                format = CalendarUtils.LONG_DATE_FORMAT
                            )
                                ?: ""
                    }
                }
            )
        binding.calendarRecyclerView.adapter = calendarAdapterHorizontal
    }

    private fun onObserveLiveData() {
        calendarViewModel.holidayLiveData.observe(this, androidx.lifecycle.Observer {

        })
    }

    private fun onAttachPageSnap() {
        val snapHelper = PagerSnapHelper() // Or LinearSnapHelper
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
    }

}
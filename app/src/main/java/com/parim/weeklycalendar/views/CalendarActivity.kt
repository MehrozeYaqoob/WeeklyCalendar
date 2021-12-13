package com.parim.weeklycalendar.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.parim.weeklycalendar.adapters.HolidayRecyclerAdapter
import com.parim.weeklycalendar.adapters.HorizontalRecyclerCalendarAdapter
import com.parim.weeklycalendar.contracts.IDateSelected
import com.parim.weeklycalendar.utils.RecyclerCalendarConfiguration
import com.parim.weeklycalendar.databinding.ActivityMainBinding
import com.parim.weeklycalendar.db.HolidayDAO
import com.parim.weeklycalendar.module.HolidayModule
import com.parim.weeklycalendar.provider.HolidayServiceProvider
import com.parim.weeklycalendar.utils.LinearLayoutManagerWithSmoothScroller
import com.parim.weeklycalendar.viewmodels.CalendarViewModel
import com.parim.weeklycalendar.viewmodels.ViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var date: Date
    private lateinit var startCal: Calendar
    private lateinit var endCal: Calendar
    private lateinit var recyclerViewConfiguration: RecyclerCalendarConfiguration
    private lateinit var calendarAdapterHorizontal:HorizontalRecyclerCalendarAdapter
    private lateinit var calendarViewModel: CalendarViewModel
    private val holidayRecyclerAdapter:HolidayRecyclerAdapter by lazy {
        HolidayRecyclerAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onInitUI()
        onProvideViewModel()
        onConfigureCalendarRecyclerView()
        onLoadCalendarRecyclerAdapter()
        onLoadHolidayRecyclerAdapter()
        onObserveLiveData()
        onAttachPageSnap()
        onLoadData(date)
//        binding.textViewSelectedDate.text =
//            CalendarUtils.dateStringFromFormat(
//                locale = recyclerViewConfiguration.calendarLocale,
//                date = date,
//                format = CalendarUtils.LONG_DATE_FORMAT
//            ) ?: ""
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

    private fun onConfigureCalendarRecyclerView() {
        recyclerViewConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )
        recyclerViewConfiguration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

    }

    private fun onLoadCalendarRecyclerAdapter(){
        calendarAdapterHorizontal =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = recyclerViewConfiguration,
                selectedDate = date,
                dateSelectListener = object : IDateSelected {
                    override fun onDateSelected(date: Date) {
                        onLoadData(date)
                    }
                }
            )
        binding.calendarRecyclerView.adapter = calendarAdapterHorizontal
    }

    private fun  onLoadHolidayRecyclerAdapter(){
        binding.holidayRecyclerView.layoutManager = LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManager.VERTICAL, false)
        val animator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
        binding.holidayRecyclerView.itemAnimator = animator
        binding.holidayRecyclerView.adapter =  holidayRecyclerAdapter
    }

    private fun onObserveLiveData() {
        calendarViewModel.holidayLiveData.observe(this, androidx.lifecycle.Observer {
            holidayRecyclerAdapter.onAddHolidayData(it)
        })
    }

    private fun onAttachPageSnap() {
        val snapHelper = PagerSnapHelper() // Or LinearSnapHelper
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
    }

    private fun onLoadData(date: Date) {
        calendarViewModel.onLoadData(this, date)
    }
}
package com.parim.weeklycalendar.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter
import android.text.format.DateFormat;
import com.parim.weeklycalendar.R


class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var date: Date
    private lateinit var startCal: Calendar
    private lateinit var endCal: Calendar
    private lateinit var recyclerViewConfiguration: RecyclerCalendarConfiguration
    private lateinit var calendarAdapterHorizontal: HorizontalRecyclerCalendarAdapter
    private lateinit var calendarViewModel: CalendarViewModel
    private val holidayRecyclerAdapter: HolidayRecyclerAdapter by lazy {
        HolidayRecyclerAdapter(this, ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onInitCalendar()
        onConfigureSpinner()
        onProvideViewModel()
        onConfigureCalendarRecyclerView()
        onLoadCalendarRecyclerAdapter()
        onLoadHolidayRecyclerAdapter()
        onObserveLiveData()
        onAttachPageSnap()
        onLoadData(date)
        onShowErrorMessage()
        onScrollViaButtons()
    }

    private fun onInitCalendar() {
        date = Date()
        date.time = System.currentTimeMillis()

        startCal = Calendar.getInstance()

        endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 1)
    }

    private fun onConfigureSpinner() {
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, applicationContext.resources.getStringArray(
            R.array.english_days))
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.root.daySpinner.adapter = aa
        binding.root.daySpinner.setSelection(0,false)
        binding.root.daySpinner.onItemSelectedListener = itemSelected
//        val dayOfTheWeek = DateFormat.format("EEEE", date) as String
//        binding.root.daySpinner.setSelection(applicationContext.resources.getStringArray(R.array.english_days).indexOf(dayOfTheWeek))
    }

    private fun onProvideViewModel() {
        calendarViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(HolidayModule(HolidayServiceProvider()), HolidayDAO())
        )[CalendarViewModel::class.java]
    }

    private fun onConfigureCalendarRecyclerView() {
        recyclerViewConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )
    }

    private fun onLoadCalendarRecyclerAdapter() {
        calendarAdapterHorizontal =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = recyclerViewConfiguration,
                selectedDate = date,
                dateSelectListener = dateSelected
            )
        binding.calendarRecyclerView.adapter = calendarAdapterHorizontal
    }

    private fun onLoadHolidayRecyclerAdapter() {
        binding.holidayRecyclerView.layoutManager =
            LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManager.VERTICAL, false)
        val animator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
        binding.holidayRecyclerView.itemAnimator = animator
        binding.holidayRecyclerView.adapter = holidayRecyclerAdapter
    }

    private fun onObserveLiveData() {
        calendarViewModel.holidayLiveData.observe(this, {
            if(it.isEmpty()){
                binding.root.textViewNoEvent.visibility=View.VISIBLE
            }else {
                binding.root.textViewNoEvent.visibility=View.GONE
            }
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

    private fun onShowErrorMessage() {
        lifecycleScope.launchWhenCreated {
            calendarViewModel.errorEventFlow.collect { errorEvent ->
                Snackbar.make(binding.root, errorEvent.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun onScrollViaButtons(){
        val offSet = 7
        var position =  0
        binding.root.btnRightArrow.setOnClickListener {
            position+=offSet
            binding.root.calendarRecyclerView.post(Runnable { binding.root.calendarRecyclerView.smoothScrollToPosition(position) })
        }
        binding.root.btnLeftArrow.setOnClickListener {
            position-=offSet
            (if (position < 0)  0 else position).also { position = it }
            binding.root.calendarRecyclerView.post(Runnable { binding.root.calendarRecyclerView.smoothScrollToPosition(position) })
        }
    }

    private val dateSelected  =  object : IDateSelected {
        override fun onDateSelected(date: Date) {
            onLoadData(date)
        }
    }

    private val  itemSelected = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
            // This is incomplete. Would need more time to solve this bug which is difficult because of tight deadlines
            calendarAdapterHorizontal.performClickOnItemView()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }
}
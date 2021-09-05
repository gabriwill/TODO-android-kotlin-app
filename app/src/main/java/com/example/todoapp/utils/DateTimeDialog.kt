package com.example.todoapp.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class DateTimeDialog {
    companion object{
        fun getDateStringFromDatePicker(fragmentManager: FragmentManager,callback: (dateString:String)->Unit?){
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time)*(-1)
                val date = Calendar.getInstance()
                date.timeInMillis = it + offset
                val dateString = CalendarUtils.getDateString(date)
                callback(dateString)
            }
            datePicker.show(fragmentManager,"DATE_PICKER_TAG")
        }

        fun getHourStringFromTimePicker(fragmentManager: FragmentManager,callback: (hourString:String)->Unit?){
            val hourPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            hourPicker.addOnPositiveButtonClickListener {
                val hour = if(hourPicker.hour<10) "0${hourPicker.hour}" else hourPicker.hour
                val minute = if(hourPicker.minute<10) "0${hourPicker.minute}" else hourPicker.minute
                val hourString = "$hour:$minute"
                callback(hourString)
            }

            hourPicker.show(fragmentManager,"HOUR_PICKER_TAG")
        }
    }
}
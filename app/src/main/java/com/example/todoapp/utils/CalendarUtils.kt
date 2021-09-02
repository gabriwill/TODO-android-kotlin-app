package com.example.todoapp.utils

import java.util.*

class CalendarUtils {
    companion object{

        fun getDateString(date: Calendar): String{
            val day = if(date.get(Calendar.DAY_OF_MONTH)<10) "0${date.get(Calendar.DAY_OF_MONTH)}"
                        else date.get(Calendar.DAY_OF_MONTH)
            val month = if((date.get(Calendar.MONTH)+1)<10) "0${(date.get(Calendar.MONTH)+1)}"
            else (date.get(Calendar.MONTH)+1)
            val year = date.get(Calendar.YEAR)
            return "$day/$month/$year"
        }
        fun getTimeString(date: Calendar): String{
            val hour = if(date.get(Calendar.HOUR_OF_DAY)<10) "0${date.get(Calendar.HOUR_OF_DAY)}"
                            else date.get(Calendar.HOUR_OF_DAY)
            val minute = if(date.get(Calendar.MINUTE)<10) "0${date.get(Calendar.MINUTE)}"
                            else date.get(Calendar.MINUTE)
            return "$hour:$minute"
        }
        fun getDaysList(date: Calendar): List<String>{
            val lastDayOfMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dayList = List(lastDayOfMonth) {
                date.set(Calendar.DAY_OF_MONTH, it+1)
                val dayString =
                    date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale("pt", "BR"))
                val dayNumber =
                    if (date.get(Calendar.DAY_OF_MONTH) < 10) "0${date.get(Calendar.DAY_OF_MONTH)}"
                    else date.get(Calendar.DAY_OF_MONTH)
                "$dayString\n$dayNumber"
            }
            return dayList
        }
    }
}
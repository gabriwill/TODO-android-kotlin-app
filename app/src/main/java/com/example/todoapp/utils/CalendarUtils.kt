package com.example.todoapp.utils

import java.util.*

class CalendarUtils {
    companion object{
        fun getMonthString(month: Int?):String{
            return when(month){
                0 ->"Janeiro"
                1 -> "Fevereiro"
                2 -> "Março"
                3 -> "Abril"
                4 -> "Maio"
                5 -> "Junho"
                6 -> "Julho"
                7 -> "Agosto"
                8 -> "Setembro"
                9 -> "Outubro"
                10 -> "Novembro"
                11 -> "Dezembro"
                else -> "Mês inválido"
            }
        }

        fun getDateString(date: Calendar): String{
            val day = if(date.get(Calendar.DAY_OF_MONTH)<10) "0${date.get(Calendar.DAY_OF_MONTH)}"
                        else date.get(Calendar.DAY_OF_MONTH)
            val month = if((date.get(Calendar.MONTH)+1)<10) "0${(date.get(Calendar.MONTH)+1)}"
            else (date.get(Calendar.MONTH)+1)
            val year = date.get(Calendar.YEAR)
            return "$day/$month/$year"
        }
        fun getHourString(date: Calendar): String{
            val hour = if(date.get(Calendar.HOUR_OF_DAY)<10) "0${date.get(Calendar.HOUR_OF_DAY)}"
                            else date.get(Calendar.HOUR_OF_DAY)
            val minute = if(date.get(Calendar.MINUTE)<10) "0${date.get(Calendar.MINUTE)}"
                            else date.get(Calendar.MINUTE)
            return "$hour:$minute"
        }
    }
}
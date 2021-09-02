package com.example.todoapp.utils

class CalendarUtils {
    companion object{
        fun getMonthString(month: Int?):String{
            return when(month){
                1 ->"Janeiro"
                2 -> "Fevereiro"
                3 -> "Março"
                4 -> "Abril"
                5 -> "Maio"
                6 -> "Junho"
                7 -> "Julho"
                8 -> "Agosto"
                9 -> "Setembro"
                10 -> "Outubro"
                11 -> "Novembro"
                0 -> "Dezembro"
                else -> "Mês inválido"
            }
        }
    }
}
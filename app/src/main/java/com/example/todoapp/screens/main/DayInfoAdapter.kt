package com.example.todoapp.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.utils.CalendarUtils
import java.util.*

class DayInfoAdapter(private val context: Context?,private val viewModel: MainScreenViewModel):
    RecyclerView.Adapter<DayInfoAdapter.ViewHolder>() {

    private var days = listOf<String>("Seg\n01")
    private var currentDay: Int = -1
    private var date:Calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = days[position]
        if(currentDay==(position+1)) {
            Log.i("date", "$currentDay - $position")
            holder.button.selectedStateStyle()
        }else holder.button.unSelectedStateStyle()
    }

    override fun getItemCount(): Int = days.size

    inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val button: Button = item.findViewById(R.id.day_info) as Button


    }

    fun update(){
        date = viewModel.currentDate.value!!
        days = CalendarUtils.getDaysList(date)
        currentDay = date.get(Calendar.DAY_OF_MONTH)
        Log.i("date", "$currentDay")
        notifyDataSetChanged()
    }

    private fun Button.selectedStateStyle(){
        setTextColor(context?.resources?.getColor(R.color.white)?:R.style.Widget_MaterialComponents_Button)
        setBackgroundColor(context?.resources?.getColor(R.color.black)?:R.style.Widget_MaterialComponents_Button)
    }
    private fun Button.unSelectedStateStyle(){
        setTextColor(context?.resources?.getColor(R.color.black)?:R.style.Widget_MaterialComponents_Button)
        setBackgroundColor(context?.resources?.getColor(R.color.white)?:R.style.Widget_MaterialComponents_Button)
    }
}
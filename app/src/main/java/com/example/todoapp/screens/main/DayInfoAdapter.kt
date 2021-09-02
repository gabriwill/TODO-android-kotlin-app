package com.example.todoapp.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class DayInfoAdapter(private val context: Context?):
    RecyclerView.Adapter<DayInfoAdapter.ViewHolder>() {

    var days = listOf<String>("")
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = days[position]
        holder.button.unSelectedStateStyle()
    }

    override fun getItemCount(): Int = days.size

    inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val button: Button = item.findViewById(R.id.day_info) as Button


    }
    fun Button.selectedStateStyle(){
        setTextColor(context?.resources?.getColor(R.color.white)?:R.style.Widget_MaterialComponents_Button)
        setBackgroundColor(context?.resources?.getColor(R.color.black)?:R.style.Widget_MaterialComponents_Button)
    }
    fun Button.unSelectedStateStyle(){
        setTextColor(context?.resources?.getColor(R.color.black)?:R.style.Widget_MaterialComponents_Button)
        setBackgroundColor(context?.resources?.getColor(R.color.white)?:R.style.Widget_MaterialComponents_Button)
    }
}
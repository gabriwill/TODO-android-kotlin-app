package com.example.todoapp.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class DayInfoAdapter: RecyclerView.Adapter<DayInfoAdapter.ViewHolder>() {
    val days = listOf<String>(
        "seg\n01",
        "ter\n02",
        "qua\n03"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = days[position]
    }

    override fun getItemCount(): Int = days.size

    inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val button: Button = item.findViewById(R.id.day_info) as Button
    }
}
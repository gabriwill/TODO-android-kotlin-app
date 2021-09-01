package com.example.todoapp.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.ToDo

class TasksListAdapter: RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {
    var taskList = listOf<ToDo>(
        ToDo("gabriel","ksjdsjds\nsdhushdu","12:15","12/08/2021",false),
        ToDo("gabriel","ksjdsjds\nsdhushdu","12:15","12/08/2021",false),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_card_view,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = taskList[position].title
        holder.dateTxt.text = taskList[position].date
        holder.hourTxt.text = taskList[position].hour
    }

    override fun getItemCount(): Int = taskList.size

    inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val titleTxt = item.findViewById(R.id.title_card_txt) as TextView
        val dateTxt = item.findViewById(R.id.date_card_txt) as TextView
        val hourTxt = item.findViewById(R.id.hour_card_txt) as TextView
    }
}
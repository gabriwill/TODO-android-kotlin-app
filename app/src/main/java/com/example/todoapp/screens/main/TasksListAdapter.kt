package com.example.todoapp.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.ToDo
import com.example.todoapp.screens.descriptionandedit.DescriptionNEditFragment
import com.example.todoapp.utils.CalendarUtils.Companion.getDateString
import com.example.todoapp.utils.CalendarUtils.Companion.getTimeString

class TasksListAdapter: RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {
    var taskList = listOf<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_card_view,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = taskList[position].title
        holder.dateTxt.text = getDateString(taskList[position].date)
        holder.hourTxt.text = getTimeString(taskList[position].date)
        holder.item.setOnClickListener { view ->
            val bundle = bundleOf(
                "title" to taskList[position].title,
                "description" to taskList[position].description,
                "date" to taskList[position].date,
                "notifEnable" to taskList[position].isNotificationEnable,
                "id" to taskList[position].id
            )
            view.findNavController().navigate(R.id.action_mainScreen_to_descriptionNEditFragment,bundle)
        }
    }

    override fun getItemCount(): Int = taskList.size

    inner class ViewHolder(val item: View): RecyclerView.ViewHolder(item) {
        val titleTxt = item.findViewById(R.id.title_card_txt) as TextView
        val dateTxt = item.findViewById(R.id.date_card_txt) as TextView
        val hourTxt = item.findViewById(R.id.hour_card_txt) as TextView
    }
}
package com.example.todoapp.screens.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.MainScreenFragmentBinding
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.DatabaseImpl
import com.example.todoapp.repository.RepositoryToDo
import com.example.todoapp.utils.CalendarUtils.Companion.getDaysList
import java.util.*

class MainScreen : Fragment() {

    companion object {
        fun newInstance() = MainScreen()
    }

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var viewModelFactory: MainScreenViewModelFactory
    private lateinit var binding: MainScreenFragmentBinding
    private lateinit var dayInfoAdapter: DayInfoAdapter
    private lateinit var taskListAdapter: TasksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_screen_fragment, container, false)

        val dataSource = DatabaseImpl(requireContext())

        viewModelFactory = MainScreenViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainScreenViewModel::class.java)

        dayInfoAdapter= DayInfoAdapter(viewModel)
        taskListAdapter = TasksListAdapter()

        binding.dayList.adapter = dayInfoAdapter
        binding.tasksList.adapter = taskListAdapter


        setClickListeners()
        setLiveDataObservers()

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )

        return binding.root
    }

    private fun setLiveDataObservers() {
        viewModel.currentDate.observe(viewLifecycleOwner,{
            binding.currentMonthYearText.text = viewModel.currentMonthYearString()
            dayInfoAdapter.update()
            viewModel.getTodoList()
        })
        viewModel.todoList.observe(viewLifecycleOwner,{ taskList ->
            taskListAdapter.taskList = taskList
            taskListAdapter.notifyDataSetChanged()
            binding.emptyListPlaceholder
                .emptyListLayout.visibility = if(taskList.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    fun setClickListeners(){
        binding.addTodoBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainScreen_to_addTodo)
        }
        binding.backArrow.setOnClickListener {
            viewModel.goToPreviousMonth()
        }
        binding.forwardArrow.setOnClickListener {
            viewModel.goToNextMonth()
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Default channel Notification"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

}
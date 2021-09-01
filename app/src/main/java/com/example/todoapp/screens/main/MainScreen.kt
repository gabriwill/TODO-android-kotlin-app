package com.example.todoapp.screens.main

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

class MainScreen : Fragment() {

    companion object {
        fun newInstance() = MainScreen()
    }

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var binding: MainScreenFragmentBinding
    private lateinit var dayInfoAdapter: DayInfoAdapter
    private lateinit var taskListAdapter: TasksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_screen_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        dayInfoAdapter= DayInfoAdapter(activity?.baseContext)
        taskListAdapter = TasksListAdapter()

        binding.dayList.adapter = dayInfoAdapter
        binding.tasksList.adapter = taskListAdapter

        setClickListeners()
        setLiveDataObservers()

        return binding.root
    }

    private fun setLiveDataObservers() {
        viewModel.currentDate.observe(viewLifecycleOwner,{
            binding.currentMonthYearText.text = viewModel.currentMonthYearString()
        })
        viewModel.todoList.observe(viewLifecycleOwner,{ taskList ->
            taskListAdapter.taskList = taskList
        })
    }

    fun setClickListeners(){
        binding.addTodoBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainScreen_to_addTodo)
        }
    }


}
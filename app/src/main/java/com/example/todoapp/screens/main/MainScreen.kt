package com.example.todoapp.screens.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.MainScreenFragmentBinding

class MainScreen : Fragment() {

    companion object {
        fun newInstance() = MainScreen()
    }

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var binding: MainScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_screen_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)
        val dayInfoAdapter= DayInfoAdapter()
        binding.dayList.adapter = dayInfoAdapter
        return binding.root
    }


}
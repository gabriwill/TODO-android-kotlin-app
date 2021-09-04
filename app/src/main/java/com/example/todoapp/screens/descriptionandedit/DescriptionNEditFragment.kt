package com.example.todoapp.screens.descriptionandedit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.DescriptionNEditFragmentBinding
import com.example.todoapp.model.ToDo
import com.example.todoapp.utils.CalendarUtils
import java.util.*

class DescriptionNEditFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionNEditFragment()
    }

    private lateinit var viewModel: DescriptionNEditViewModel
    private lateinit var binding: DescriptionNEditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.description_n_edit_fragment,container,false)

        val toDo = getTodoFromArguments()
        showTodoInfo(toDo)

        viewModel = ViewModelProvider(this).get(DescriptionNEditViewModel::class.java)
        return binding.root
    }

    private fun showTodoInfo(toDo: ToDo){
        binding.titleTextField.editText?.setText(toDo.title)
        binding.descriptionTextField.editText?.setText(toDo.description)
        binding.dateTextField.editText?.setText(CalendarUtils.getDateString(toDo.date))
        binding.hourTextField.editText?.setText(CalendarUtils.getTimeString(toDo.date))
        binding.notificationSwitchState.isChecked = toDo.isNotificationEnable
    }

    private fun getTodoFromArguments():ToDo{
        return ToDo(arguments?.getString("title")?:"",
                arguments?.getString("description")?:"",
                          arguments?.get("date") as Calendar,
            arguments?.getBoolean("notifEnable")?: false,
                      arguments?.getLong("id")?:0
        )
    }

}
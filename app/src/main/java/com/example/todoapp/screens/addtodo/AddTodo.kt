package com.example.todoapp.screens.addtodo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.AddTodoFragmentBinding
import com.example.todoapp.repository.DatabaseImpl
import com.example.todoapp.utils.CalendarUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTodo : Fragment() {

    companion object {
        fun newInstance() = AddTodo()
    }

    private lateinit var viewModel: AddTodoViewModel
    private lateinit var binding: AddTodoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.add_todo_fragment,container,false)

        val dataSource = DatabaseImpl(requireContext())

        val factory = AddTodoViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this,factory).get(AddTodoViewModel::class.java)

        setOnClickListeners()
        setOnChangeTextListeners()

        return binding.root
    }

    private fun setOnChangeTextListeners(){
        binding.titleTextField.editText?.addTextChangedListener {
            if(it?.length==0) binding.titleTextField.error = "Digite um titulo"
            else binding.titleTextField.error = null

        }
        binding.dateTextField.editText?.addTextChangedListener {
            if(it?.length==0) binding.dateTextField.error = "Selecione uma data"
            else binding.dateTextField.error = null

        }
        binding.hourTextField.editText?.addTextChangedListener {
            if(it?.length==0) binding.hourTextField.error = "Selecione uma horário"
            else binding.hourTextField.error = null

        }
    }

    private fun setOnClickListeners(){
        binding.dateTextField.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                .build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time)*(-1)
                val date = Calendar.getInstance()
                date.timeInMillis = it + offset
                val dateString = CalendarUtils.getDateString(date)
                binding.dateTextField.editText?.setText(dateString)
            }
            datePicker.show(childFragmentManager,"DATE_PICKER_TAG")
        }

        binding.hourTextField.editText?.setOnClickListener {
            val hourPicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            hourPicker.addOnPositiveButtonClickListener {
                val hour = if(hourPicker.hour<10) "0${hourPicker.hour}" else hourPicker.hour
                val minute = if(hourPicker.minute<10) "0${hourPicker.minute}" else hourPicker.minute
                val hourString = "$hour:$minute"
                binding.hourTextField.editText?.setText(hourString)
            }
            hourPicker.show(childFragmentManager,"HOUR_PICKER_TAG")
        }

        binding.addTaskBtn.setOnClickListener {
            val isTitleEmpty = binding.titleTextField.editText?.text?.length==0
            val isDateEmpty = binding.dateTextField.editText?.text?.length==0
            val isHourEmpty = binding.hourTextField.editText?.text?.length==0
            if(isTitleEmpty || isDateEmpty || isHourEmpty) {
                if(isTitleEmpty) binding.titleTextField.error = "Digite um titulo"
                if(isDateEmpty) binding.dateTextField.error = "Selecione uma data"
                if(isHourEmpty) binding.hourTextField.error = "Selecione uma horário"
                return@setOnClickListener
            }
            saveTodo(it)
        }

        binding.cancelAddBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addTodo_to_mainScreen)
        }
    }

    private fun saveTodo(view: View) {
        viewModel.createTodo(
            binding.titleTextField.editText?.text.toString(),
            binding.descriptionTextField.editText?.text.toString(),
            binding.dateTextField.editText?.text.toString(),
            binding.hourTextField.editText?.text.toString(),
            binding.notificationSwitchState.isChecked,
            view
        )
    }

}
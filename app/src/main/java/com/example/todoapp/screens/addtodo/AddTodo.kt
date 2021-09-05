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
import com.example.todoapp.utils.DateTimeDialog
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
            DateTimeDialog.getDateStringFromDatePicker(childFragmentManager){
                binding.dateTextField.editText?.setText(it)
            }
        }

        binding.hourTextField.editText?.setOnClickListener {
            DateTimeDialog.getHourStringFromTimePicker(childFragmentManager){
                binding.hourTextField.editText?.setText(it)
            }
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
            findNavController().popBackStack()
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
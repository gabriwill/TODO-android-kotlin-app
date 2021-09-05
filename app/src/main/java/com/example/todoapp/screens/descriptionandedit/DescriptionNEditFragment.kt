package com.example.todoapp.screens.descriptionandedit

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.DescriptionNEditFragmentBinding
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.DatabaseImpl
import com.example.todoapp.utils.CalendarUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class DescriptionNEditFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionNEditFragment()
        const val DESCRIPTION_MODE: Int = 0
        const val EDIT_MODE: Int = 1
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

        val dataSource = DatabaseImpl(requireContext())
        val factory = DescriptionNEditViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory).get(DescriptionNEditViewModel::class.java)

        setObservers()
        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners(){
        binding.editTodoBtn.setOnClickListener {
            if(viewModel.screenMode.value == DESCRIPTION_MODE){
                viewModel.changeToEditMode()
            }
        }
        binding.updateTodoBtn.setOnClickListener {
            if(viewModel.screenMode.value == EDIT_MODE){
                val isTitleEmpty = binding.titleTextField.editText?.text?.length==0
                val isDateEmpty = binding.dateTextField.editText?.text?.length==0
                val isHourEmpty = binding.hourTextField.editText?.text?.length==0
                if(isTitleEmpty || isDateEmpty || isHourEmpty) {
                    if(isTitleEmpty) binding.titleTextField.error = "Digite um titulo"
                    if(isDateEmpty) binding.dateTextField.error = "Selecione uma data"
                    if(isHourEmpty) binding.hourTextField.error = "Selecione uma horário"
                    return@setOnClickListener
                }
                updateTodo(it)
            }
        }
        binding.cancelEditBtn.setOnClickListener {
            if(viewModel.screenMode.value == EDIT_MODE){
                viewModel.changeToDescriptionMode()
                val toDo = getTodoFromArguments()
                showTodoInfo(toDo)
            }
        }
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
    }

    private fun updateTodo(view: View) {
        viewModel.updateTodo(
            arguments?.getLong("id")!!,
            binding.titleTextField.editText?.text.toString(),
            binding.descriptionTextField.editText?.text.toString(),
            binding.dateTextField.editText?.text.toString(),
            binding.hourTextField.editText?.text.toString(),
            binding.notificationSwitchState.isChecked,
            view
        )
    }

    private fun setObservers() {
        viewModel.screenMode.observe(viewLifecycleOwner,{ mode ->
            if(mode== DESCRIPTION_MODE){
                setDescriptionMode()
            }
            if(mode== EDIT_MODE){
                setEditMode()
            }
        })
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

    private fun setDescriptionMode(){
        binding.titleTextField.isEnabled = false
        binding.descriptionTextField.isEnabled = false
        binding.dateTextField.isEnabled = false
        binding.hourTextField.isEnabled = false
        binding.notificationSwitchState.isEnabled = false

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Detalhes da tarefa"

        binding.cancelEditBtn.hide()
        binding.updateTodoBtn.hide()
        binding.editTodoBtn.isClickable = true
        binding.editTodoBtn.visibility = View.VISIBLE
    }

    private fun setEditMode(){
        binding.titleTextField.isEnabled = true
        binding.descriptionTextField.isEnabled = true
        binding.dateTextField.isEnabled = true
        binding.hourTextField.isEnabled = true
        binding.notificationSwitchState.isEnabled = true

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Editar a tarefa"

        binding.cancelEditBtn.show()
        binding.updateTodoBtn.show()
        binding.editTodoBtn.isClickable = false
        binding.editTodoBtn.visibility = View.INVISIBLE
    }

}
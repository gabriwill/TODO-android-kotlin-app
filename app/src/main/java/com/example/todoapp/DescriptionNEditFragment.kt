package com.example.todoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DescriptionNEditFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionNEditFragment()
    }

    private lateinit var viewModel: DescriptionNEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.description_n_edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DescriptionNEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
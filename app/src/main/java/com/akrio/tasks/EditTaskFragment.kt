package com.akrio.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.akrio.tasks.databinding.FragmentEditTaskBinding
import com.akrio.tasks.databinding.FragmentTasksBinding
import models.TaskDatabase

class EditTaskFragment : Fragment() {

    private lateinit var binding: FragmentEditTaskBinding

    private lateinit var viewModel: EditTaskViewModel
    private lateinit var viewModelFactory: EditTaskViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        viewModelFactory = EditTaskViewModelFactory(taskId,dao)
        viewModel = ViewModelProvider(this,viewModelFactory)[EditTaskViewModel::class.java]

        viewModel.task.value?.let {
            binding.taskName.setText(it.taskName)
        }

        viewModel.task.value?.let {
            binding.taskDone.isChecked = it.taskDone
        }


        viewModel.navigateToList.observe(viewLifecycleOwner){
            if(it){
                view.findNavController().navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        }

        binding.taskDone.setOnClickListener {
            viewModel.task.value?.let {
                it.taskDone = binding.taskDone.isChecked
            }
        }

        binding.updateButton.setOnClickListener {
            viewModel.updateTask()
        }

        binding.updateButton.setOnClickListener {
            viewModel.deleteTask()
        }

        return view
    }

}
package com.akrio.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.akrio.tasks.databinding.FragmentEditTaskBinding
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

        viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditTaskViewModel::class.java]


        viewModel.task.observe(viewLifecycleOwner) {
            it?.let {
                binding.taskName.setText(it.taskName)
                binding.taskDone.isChecked = it.taskDone
            }
        }

        binding.taskDone.setOnClickListener {
            viewModel.editedTaskValue.observe(viewLifecycleOwner) {
                viewModel.editedTaskValue.value?.let {
                    it.taskDone = binding.taskDone.isChecked
                }
            }
        }

        binding.updateButton.setOnClickListener {
            viewModel.editedTaskValue.observe(viewLifecycleOwner) {
                viewModel.editedTaskValue.value?.taskName = binding.taskName.text.toString()
                viewModel.updateTask()
                navigateToTaskFragment()
        }
    }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteTask()
            navigateToTaskFragment()
        }

        return view
    }

    private fun navigateToTaskFragment(){
        view?.findNavController()?.navigate(R.id.action_editTaskFragment_to_tasksFragment)
    }

}
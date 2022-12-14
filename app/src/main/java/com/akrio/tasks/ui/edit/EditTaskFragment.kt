package com.akrio.tasks.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.akrio.tasks.R
import com.akrio.tasks.data.db.TaskDatabase
import com.akrio.tasks.databinding.FragmentEditTaskBinding

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
            if (binding.taskDone.isChecked) {
                viewModel.taskDone()
            } else {
                viewModel.taskNotDone()
            }
        }

        binding.taskName.addTextChangedListener {
            viewModel.updateTaskName(it.toString())
        }

        binding.updateButton.setOnClickListener {
            viewModel.updateTask()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteTask()
        }

        viewModel.readyToNavigate.observe(viewLifecycleOwner) {
            if (it) {
                navigateToTaskFragment()
            }
        }

        return view
    }

    private fun navigateToTaskFragment() {
        view?.findNavController()?.navigate(R.id.action_editTaskFragment_to_tasksFragment)
        viewModel.onTaskNavigated()
    }

}
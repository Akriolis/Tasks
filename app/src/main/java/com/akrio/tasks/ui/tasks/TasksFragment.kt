package com.akrio.tasks.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akrio.tasks.R
import com.akrio.tasks.databinding.FragmentTasksBinding
import com.akrio.tasks.data.db.TaskDatabase
import com.akrio.tasks.ui.tasks.recycler.TaskItemAdapter

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TasksViewModel
    private lateinit var viewModelFactory: TasksViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater,container,false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        viewModelFactory = TasksViewModelFactory(dao)
        viewModel = ViewModelProvider(this,viewModelFactory)[TasksViewModel::class.java]

        val adapter = TaskItemAdapter {
            viewModel.onTaskClicked(it)
        }

        binding.tasksList.adapter = adapter

        viewModel.navigateToTask.observe(viewLifecycleOwner){
            it?.let{
                val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(it)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner){
            it?.let{
                adapter.submitList(it)
                binding.tasksList.smoothScrollToPosition(it.size)
            }
        }

        binding.taskName.addTextChangedListener {
            viewModel.addNewTaskName(it.toString())
        }

        binding.saveButton.setOnClickListener {
            if(binding.taskName.text.trim().isEmpty()){
                Toast.makeText(activity,getString(R.string.task_name_blank),Toast.LENGTH_LONG).show()
            } else {
                viewModel.addTask()
                binding.taskName.text.clear()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
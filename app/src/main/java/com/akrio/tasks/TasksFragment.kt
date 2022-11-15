package com.akrio.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrio.tasks.databinding.FragmentTasksBinding
import models.TaskDatabase

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: TasksViewModel
    lateinit var viewModelFactory: TasksViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater,container,false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        viewModelFactory = TasksViewModelFactory(dao)
        viewModel = ViewModelProvider(this,viewModelFactory)[TasksViewModel::class.java]

        val adapter = TaskItemAdapter()
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner){
            it?.let{
                adapter.data = it
            }
        }

        binding.saveButton.setOnClickListener {
            if(binding.taskName.text.trim().isEmpty()){
                Toast.makeText(activity,getString(R.string.task_name_blank),Toast.LENGTH_LONG).show()
            } else {
                viewModel.addNewTaskName(binding.taskName.text.toString())
                viewModel.addTask()
                binding.taskName.text.clear()
            }
        }

        binding.clearLists.setOnClickListener {
                viewModel.deleteAll()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
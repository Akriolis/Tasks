package com.akrio.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akrio.tasks.databinding.TaskItemBinding
import models.Task

class TaskItemAdapter: ListAdapter<Task,TaskItemAdapter.TaskViewHolder>(TaskDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.taskName.text = item.taskName
        holder.binding.taskDone.isChecked = item.taskDone
    }

    class TaskViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root)
}


package com.akrio.tasks.ui.tasks.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akrio.tasks.R
import com.akrio.tasks.databinding.TaskItemBinding
import com.akrio.tasks.data.db.models.Task

class TaskItemAdapter(val clickListener: (taskId: Long) -> Unit) :
    ListAdapter<Task, TaskItemAdapter.TaskViewHolder>(
        AsyncDifferConfig.Builder<Task> (TaskDiffItemCallback()).build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        val contextOfViewHolder = holder.binding.taskDone.context

        holder.binding.taskName.text = item.taskName
        holder.binding.taskDone.isChecked = item.taskDone

        if (item.taskDone) {
            holder.binding.taskDoneText.text = contextOfViewHolder.getString(R.string.done)
        } else {
            holder.binding.taskDoneText.text = contextOfViewHolder.getString(R.string.not_done)
        }

        //here i using .root instead of .cardView
        holder.binding.root.setOnClickListener {
            clickListener(item.taskId)

        }

    }

    // you can grab View itself by .root or you can set an id (like i did here)
    class TaskViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)
}


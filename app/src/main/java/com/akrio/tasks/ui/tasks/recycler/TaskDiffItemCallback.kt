package com.akrio.tasks.ui.tasks.recycler

import androidx.recyclerview.widget.DiffUtil
import com.akrio.tasks.data.db.models.Task

class TaskDiffItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = (oldItem.taskId == newItem.taskId)

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = (oldItem == newItem)
}
package com.akrio.tasks

import androidx.recyclerview.widget.DiffUtil
import models.Task

class TaskDiffItemCallback: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = (oldItem.taskId == newItem.taskId)

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = (oldItem == newItem)
}
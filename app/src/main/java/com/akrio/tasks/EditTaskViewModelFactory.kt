package com.akrio.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import models.TaskDao

class EditTaskViewModelFactory(private val taskId: Long,
                               private val dao: TaskDao ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditTaskViewModel(taskId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
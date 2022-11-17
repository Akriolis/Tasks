package com.akrio.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.TaskDao

class EditTaskViewModel (taskId: Long, val dao: TaskDao): ViewModel() {

    val task = dao.get(taskId)

    val editedTaskValue = task

    fun updateTask(){
        viewModelScope.launch (Dispatchers.IO) {
                dao.update(editedTaskValue.value!!)
        }
    }

    fun deleteTask(){
        viewModelScope.launch (Dispatchers.IO) {
                dao.delete(task.value!!)
        }
    }

}
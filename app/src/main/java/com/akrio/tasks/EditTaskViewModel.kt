package com.akrio.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.TaskDao

class EditTaskViewModel (taskId: Long, val dao: TaskDao): ViewModel() {

    private val _task = dao.get(taskId)
    val task get() = _task

    private val _editedTaskValue = _task

    fun updateTask(){
        viewModelScope.launch (Dispatchers.IO) {
                dao.update(_editedTaskValue.value!!)
        }
    }

    fun deleteTask(){
        viewModelScope.launch (Dispatchers.IO) {
                dao.delete(_task.value!!)
        }
    }

    fun taskDone(){
        _editedTaskValue.value?.taskDone = true
    }

    fun taskNotDone(){
        _editedTaskValue.value?.taskDone = false
    }

    fun updateTaskName(taskName: String){
        _editedTaskValue.value?.taskName = taskName
    }

}
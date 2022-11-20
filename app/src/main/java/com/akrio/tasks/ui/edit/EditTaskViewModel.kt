package com.akrio.tasks.ui.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akrio.tasks.data.db.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, val dao: TaskDao) : ViewModel() {

    private val _task = dao.get(taskId)
    val task get() = _task

    private val _editedTaskValue = _task
    private val _changedTaskName = MutableLiveData(_editedTaskValue.value?.taskName)

    private val _readyToNavigate = MutableLiveData(false)
    val readyToNavigate get() = _readyToNavigate

    fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            if(_editedTaskValue.value?.taskName != _changedTaskName.value){
                _editedTaskValue.value?.taskName = _changedTaskName.value ?: _editedTaskValue.value?.taskName!!
            }
            dao.update(_editedTaskValue.value!!)
            _readyToNavigate.postValue(true)
        }
    }

    fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(_task.value!!)
            _readyToNavigate.postValue(true)
        }
    }

    fun taskDone() {
        _editedTaskValue.value?.taskDone = true
    }

    fun taskNotDone() {
        _editedTaskValue.value?.taskDone = false
    }

    fun updateTaskName(taskName: String) {
        _changedTaskName.value = taskName
    }

    fun onTaskNavigated(){
        _readyToNavigate.postValue(false)
    }

}
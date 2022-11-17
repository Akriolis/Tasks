package com.akrio.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.*

class TasksViewModel(val dao: TaskDao): ViewModel() {

    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
    get() = _navigateToTask

    private var newTaskName = ""

    val tasks = dao.getAll()
//    val tasksString = Transformations.map(tasks){
//        tasks -> formatTasks(tasks)
//    }
//
//    private fun formatTasks(tasks: List<Task>): String{
//        return tasks.fold(""){
//                str, item -> str + '\n' + formatTask(item)
//        }
//    }
//
//    private fun formatTask(task: Task): String{
//        var str = "ID: ${task.taskId}"
//        str += '\n' + "Name: ${task.taskName}"
//        str += '\n' + "Complete: ${task.taskDone}" + '\n'
//        return str
//    }

    fun addNewTaskName(taskName: String){
        newTaskName = taskName

    }

    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    fun addTask() {
        viewModelScope.launch (Dispatchers.IO){
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    fun onTaskClicked(taskId: Long){
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated(){
        _navigateToTask.value = null
    }



}
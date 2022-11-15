package com.akrio.tasks

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.*

class TasksViewModel(val dao: TaskDao): ViewModel() {
    private var newTaskName = ""

    val tasks = dao.getAll()
    val tasksString = Transformations.map(tasks){
        tasks -> formatTasks(tasks)
    }

    private fun formatTasks(tasks: List<Task>): String{
        return tasks.fold(""){
                str, item -> str + '\n' + formatTask(item)
        }
    }

    private fun formatTask(task: Task): String{
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'
        return str
    }

    fun addNewTaskName(taskName: String){
        newTaskName = taskName

    }

    fun deleteById(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(Task(taskId = userId))
        }
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



}
package com.akrio.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akrio.tasks.databinding.TaskItemBinding
import models.Task

class TaskItemAdapter: RecyclerView.Adapter<TaskItemAdapter.TaskViewHolder>() {

    var data = listOf<Task>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = TaskItemBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = data[position]
        holder.binding.taskView.text = item.taskName
    }

    override fun getItemCount(): Int = data.size

//    fun listsUpdated(){
//        notifyItemInserted(data.size-1)
//    }

    class TaskViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root)
}


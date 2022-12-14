package com.akrio.tasks.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.akrio.tasks.data.db.models.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    //@Insert
    //fun insertAll(task: List<Task>)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table WHERE taskId = :key")
    fun get(key: Long): LiveData<Task>

    //DESC means "in descending order"
    //@Query("SELECT * FROM task_table ORDER BY taskId DESC")
    @Query("SELECT * FROM task_table")
    fun getAll(): LiveData<List<Task>>

}
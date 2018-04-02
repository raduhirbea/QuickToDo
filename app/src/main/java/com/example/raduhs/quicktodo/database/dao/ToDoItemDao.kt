package com.example.raduhs.quicktodo.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.raduhs.quicktodo.database.entities.ToDoItem

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todo_items")
    fun getTodoItems(): LiveData<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodoItems(todoItems: List<ToDoItem>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTodoItem(todoItem: ToDoItem)

    @Delete
    fun deleteTodoItem(todoItem: ToDoItem)

    @Delete
    fun deleteTodoItems(todoItems: List<ToDoItem>)
}
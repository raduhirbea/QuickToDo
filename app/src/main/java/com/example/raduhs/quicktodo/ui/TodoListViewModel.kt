package com.example.raduhs.quicktodo.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.raduhs.quicktodo.ToDoApplication
import com.example.raduhs.quicktodo.database.TodoAppDatabase
import com.example.raduhs.quicktodo.database.entities.ToDoItem
import javax.inject.Inject

class TodoListViewModel constructor(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var db: TodoAppDatabase

    init {
        (application as ToDoApplication).appComponent.inject(this)
    }

    fun getTodoListItems() = db.todoItemDao().getTodoItems()

    fun createTodoItem(todoItems: List<ToDoItem>) = Thread(Runnable { db.todoItemDao().insertTodoItems(todoItems) }).start()

    fun updateTodoItem(todoItem: ToDoItem) = Thread(Runnable { db.todoItemDao().updateTodoItem(todoItem) }).start()

    fun deleteTodoItem(todoItem: ToDoItem) = Thread(Runnable { db.todoItemDao().deleteTodoItem(todoItem) }).start()
}
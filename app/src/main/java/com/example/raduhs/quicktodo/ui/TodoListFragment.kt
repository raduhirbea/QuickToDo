package com.example.raduhs.quicktodo.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.raduhs.quicktodo.R
import com.example.raduhs.quicktodo.database.entities.ToDoItem

class TodoListFragment : LifecycleFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val todoListOverviewFragment = inflater.inflate(R.layout.fragment_todo_list, container, false)
        val todoListItemsRecyclerView = todoListOverviewFragment.findViewById<RecyclerView>(R.id.todo_list_items_recycler_view)

        val todoListDetailViewModel = ViewModelProviders.of(this).get(TodoListViewModel::class.java)

        todoListDetailViewModel.getTodoListItems().observe(this, Observer<List<ToDoItem>> {
            val newTodoItem = ToDoItem(0, "", "", false)
            val todoItems = it?.toMutableList()
            todoItems?.add(newTodoItem)
            val todoListDetailAdapter = TodoListAdapter(todoItems, context) { todoItem, actionType ->
                when (actionType) {
                    ToDoItem.CREATE -> todoListDetailViewModel.createTodoItem(listOf(todoItem))
                    ToDoItem.UPDATE -> todoListDetailViewModel.updateTodoItem(todoItem)
                    ToDoItem.DELETE -> todoListDetailViewModel.deleteTodoItem(todoItem)
                }
            }

            todoListItemsRecyclerView.adapter = todoListDetailAdapter
            val layoutManager = LinearLayoutManager(this.context)
            layoutManager.stackFromEnd = true
            todoListItemsRecyclerView.layoutManager = layoutManager
        })

        return todoListOverviewFragment
    }
}
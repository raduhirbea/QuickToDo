package com.example.raduhs.quicktodo.ui

import android.content.Context
import android.graphics.Paint
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.TextView
import com.example.raduhs.quicktodo.R
import com.example.raduhs.quicktodo.database.entities.ToDoItem
import com.example.raduhs.quicktodo.util.openKeyboard

class TodoListAdapter(
        val todoItems: List<ToDoItem>?,
        val context: Context,
        inline val onTodoItemAction: (todoItem: ToDoItem, actionType: Int) -> Unit): RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        todoItems?.let {
            val todoItem = todoItems[position]

            if (todoItem.title.isEmpty()) {
                viewHolder.todoListDetailItemTextView.performClick()

                viewHolder.toggleEditCreate()

                viewHolder.todoListDetailItemEditTextLayout.requestFocus()

                viewHolder.todoListDetailItemEditTextLayout.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        createTodoItem(viewHolder, todoItem)
                    }
                }

                viewHolder.todoListDetailItemEditTextLayout.setOnEditorActionListener{ _, keyCode, _ ->
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        createTodoItem(viewHolder, todoItem)
                    }
                    true
                }

                viewHolder.todoListDetailItemEditSaveButton.setOnClickListener {
                    createTodoItem(viewHolder, todoItem)
                }
            } else {
                viewHolder.todoListDetailItemTextView.text = todoItem.title

                if (todoItem.completed) {
                    viewHolder.todoListDetailItemTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    viewHolder.todoListDetailItemTextView.paintFlags = 0
                }

                viewHolder.todoListDetailItemCheckBox.isChecked = todoItem.completed

                setUpItemTextViewListener(viewHolder, todoItem)

                viewHolder.todoListDetailDeleteItemButton.setOnClickListener {
                    onTodoItemAction(todoItem, ToDoItem.DELETE)
                }

                viewHolder.todoListDetailItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    todoItem.completed = isChecked
                    onTodoItemAction(todoItem, ToDoItem.UPDATE)
                }
            }

        }
    }

    private fun setUpItemTextViewListener(viewHolder: ViewHolder, todoItem: ToDoItem) {
        viewHolder.todoListDetailItemTextView.setOnClickListener {
            viewHolder.todoListDetailItemEditTextLayout.setText(todoItem.title)

            viewHolder.toggleEditCreate()

            viewHolder.todoListDetailItemEditTextLayout.requestFocus()
            context.openKeyboard()

            viewHolder.todoListDetailItemEditTextLayout.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    updateTodoItem(viewHolder, todoItem)
                }
            }

            viewHolder.todoListDetailItemEditTextLayout.setOnEditorActionListener { _, keyCode, _ ->
                if (keyCode == EditorInfo.IME_ACTION_DONE) {
                    updateTodoItem(viewHolder, todoItem)
                }
                true
            }

            viewHolder.todoListDetailItemEditSaveButton.setOnClickListener {
                updateTodoItem(viewHolder, todoItem)
            }
        }
    }

    private fun updateTodoItem(viewHolder: ViewHolder, todoItem: ToDoItem) {
        todoItemAction(viewHolder, todoItem) {
            onTodoItemAction(it, ToDoItem.UPDATE)
        }
    }

    private fun createTodoItem(viewHolder: ViewHolder, todoItem: ToDoItem) {
        todoItemAction(viewHolder, todoItem) {
            onTodoItemAction(it, ToDoItem.CREATE)
        }
    }

    private inline fun todoItemAction(viewHolder: ViewHolder, todoItem: ToDoItem, action: (todoItem: ToDoItem) -> Unit ) {
        val textValue = viewHolder.todoListDetailItemEditTextLayout.text.toString()

        if (textValue != todoItem.title) {
            todoItem.title = viewHolder.todoListDetailItemEditTextLayout.text.toString()
            action(todoItem)
        }

        viewHolder.toggleEditCreate()
    }

    override fun getItemCount(): Int {
        if (todoItems != null) {
            return todoItems.size
        } else {
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val todoListDetailItems = inflater.inflate(R.layout.todo_list_item, parent, false)

        return ViewHolder(todoListDetailItems)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoListDetailItemTextView: TextView by lazy {
            itemView.findViewById<TextView>(R.id.todo_list_detail_item_text_view)
        }

        val todoListDetailItemCheckBox: CheckBox by lazy {
            itemView.findViewById<CheckBox>(R.id.todo_list_detail_item_check_box)
        }

        val todoListDetailItemEditLayout: ConstraintLayout by lazy {
            itemView.findViewById<ConstraintLayout>(R.id.todo_list_item_edit_layout)
        }

        val todoListDetailItemEditSaveButton: AppCompatImageButton by lazy {
            itemView.findViewById<AppCompatImageButton>(R.id.todo_list_item_edit_save_button)
        }

        val todoListDetailItemEditTextLayout: TextInputEditText by lazy {
            itemView.findViewById<TextInputEditText>(R.id.todo_list_item_edit_input_edit_text)
        }

        val todoListDetailDeleteItemButton: AppCompatImageButton by lazy {
            itemView.findViewById<AppCompatImageButton>(R.id.todo_list_item_delete_button)
        }

        val todoListDetailItemViewLayout: ConstraintLayout by lazy {
            itemView.findViewById<ConstraintLayout>(R.id.todo_list_item_view_layout)
        }

        fun toggleEditCreate() {
            if (todoListDetailItemViewLayout.visibility == View.VISIBLE &&
                    todoListDetailItemEditLayout.visibility == View.GONE) {
                todoListDetailItemViewLayout.visibility = View.GONE
                todoListDetailItemEditLayout.visibility = View.VISIBLE
            } else {
                todoListDetailItemViewLayout.visibility = View.VISIBLE
                todoListDetailItemEditLayout.visibility = View.GONE
            }
        }
    }
}

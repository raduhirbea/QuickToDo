package com.example.raduhs.quicktodo.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName="todo_items")
data class ToDoItem(@PrimaryKey(autoGenerate = true)
                    var id: Long,
                    var title: String,
                    var description: String,
                    var completed: Boolean
                   ) {

    companion object {
        const val CREATE = 0
        const val UPDATE = 1
        const val DELETE = 2
    }
}

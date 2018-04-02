package com.example.raduhs.quicktodo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.raduhs.quicktodo.database.dao.TodoItemDao
import com.example.raduhs.quicktodo.database.entities.ToDoItem

@Database(version = 1, entities = arrayOf(ToDoItem::class))
abstract class TodoAppDatabase: RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        private const val DB_NAME = "quicktodoapp.db"

        fun createInMemoryDatabase(context: Context): TodoAppDatabase
                = Room.inMemoryDatabaseBuilder(context.applicationContext, TodoAppDatabase::class.java).build()

        fun createPersistentDatabase(context: Context): TodoAppDatabase
                = Room.databaseBuilder(context.applicationContext, TodoAppDatabase::class.java, DB_NAME).build()
    }
}
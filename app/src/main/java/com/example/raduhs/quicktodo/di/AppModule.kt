package com.example.raduhs.quicktodo.di

import android.content.Context
import com.example.raduhs.quicktodo.database.TodoAppDatabase
import com.example.raduhs.quicktodo.database.dao.TodoItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideDb(context: Context): TodoAppDatabase {
        return TodoAppDatabase.createPersistentDatabase(context)
    }


    @Singleton
    @Provides
    fun provideTodoItemDao(db: TodoAppDatabase): TodoItemDao {
        return db.todoItemDao()
    }
}
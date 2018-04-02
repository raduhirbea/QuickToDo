package com.example.raduhs.quicktodo.di

import com.example.raduhs.quicktodo.ui.TodoListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        AppModule::class
)
)
interface AppComponent {

    fun inject(todoListViewModel: TodoListViewModel)

}
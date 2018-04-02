package com.example.raduhs.quicktodo

import android.app.Application
import com.example.raduhs.quicktodo.di.AndroidModule
import com.example.raduhs.quicktodo.di.AppComponent
import com.example.raduhs.quicktodo.di.DaggerAppComponent

class ToDoApplication: Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
            .androidModule(AndroidModule(this))
            .build()




}

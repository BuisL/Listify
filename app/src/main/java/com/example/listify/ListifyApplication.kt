package com.example.listify

import android.app.Application
import com.example.listify.di.AppContainer
import com.example.listify.di.DefaultAppContainer
import com.example.listify.data.local.DatabaseContainer
import com.example.listify.data.local.DefaultDatabaseContainer

class ListifyApplication : Application() {

    lateinit var container: AppContainer
    lateinit var containerDB: DatabaseContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        containerDB = DefaultDatabaseContainer(this)
    }
}
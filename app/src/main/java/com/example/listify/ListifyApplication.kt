package com.example.listify

import android.app.Application
import com.example.listify.data.AppContainer
import com.example.listify.data.DefaultAppContainer
import com.example.listify.data.database.DatabaseContainer
import com.example.listify.data.database.DefaultDatabaseContainer

class ListifyApplication : Application() {

    lateinit var container: AppContainer
    lateinit var containerDB: DatabaseContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        containerDB = DefaultDatabaseContainer(this)
    }
}
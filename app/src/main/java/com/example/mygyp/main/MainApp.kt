package com.example.mygyp.main

import android.app.Application
import com.example.mygyp.models.UserMemStore
import com.example.mygyp.models.UserModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val users = UserMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

    }
}
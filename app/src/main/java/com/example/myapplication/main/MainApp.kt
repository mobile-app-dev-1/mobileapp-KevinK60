package com.example.myapplication.main
import android.app.Application
import com.example.myapplication.models.UsersStore
import com.example.myapplication.models.userMemStore
import timber.log.Timber.i
import timber.log.Timber

class MainApp : Application() {

    val users = userMemStore()
    val mainApp = applicationContext as MainApp

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Gym app has started")
    }
}
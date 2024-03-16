package com.example.myapplication.main
import android.app.Application
import com.example.myapplication.models.UsersModel
import com.example.myapplication.models.User

class MainApp : Application() {
    val users = ArrayList<User>()

    override fun onCreate() {
        super.onCreate()
        timber.plant(timber.DebugTree())
        i("Gym app has started")
    }
}
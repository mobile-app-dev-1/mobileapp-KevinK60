package com.example.mygyp.main

import android.app.Application
import com.example.mygyp.models.PlacemarkMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    // val placemarks = ArrayList<PlacemarkModel>()
    val placemarks = PlacemarkMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

    }
}
package com.example.mygyp.main

import android.app.Application
// import com.example.mygyp.models.UserFirebaseStore
import com.example.mygyp.models.UserSQLStore
import com.example.mygyp.models.UserStore
import timber.log.Timber

/**
 * Custom Application class for initializing the application-wide components.
 */
class MainApp : Application() {
    /**
     * * Instance of the in-memory user data store.
     *
     * */
    lateinit var users: UserStore

    /**
     * Called when the application is starting.
     *
     * This method initializes the Timber logging library for debugging purposes
     * and logs a message indicating the start of the application.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        users = UserSQLStore(applicationContext)
        Timber.i("Application started")
    }
}

package com.example.mygyp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mygyp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Add any animations or transitions here

        // Wait for a certain duration (e.g., 2 seconds) and then navigate to the main activity
        val splashDuration = 2000L // 2 seconds
        Thread {
            Thread.sleep(splashDuration)
            startActivity(Intent(this, UserListActivity::class.java))
            finish()
        }.start()    }
}
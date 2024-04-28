package com.example.mygyp.activities

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygyp.databinding.ActivitySettingActivitvyBinding

class SettingActivitvy : AppCompatActivity() {
    companion object {
        private const val TAG = "SettingActivity"
        private const val THEME_PREF = "theme_preference"
        private const val IS_DARK_THEME = "is_dark_theme"
    }

    private lateinit var binding: ActivitySettingActivitvyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme() // Apply theme based on the saved preference before setting the content view
        binding = ActivitySettingActivitvyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleButton.isChecked = getThemePreference()

        binding.toggleButton.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            saveThemePreference(isChecked)
            Log.i(TAG, if (isChecked) "Switched to Dark Theme" else "Switched to Light Theme")
            recreate() // Recreate the activity to apply the theme change
        }

        // Adjust padding based on system bars to handle insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the back navigation
        binding.button2.setOnClickListener {
            onBackPressed()
        }
    }

    private fun applyTheme() {
        setTheme(if (getThemePreference()) com.example.mygyp.R.style.AppTheme_users_dark else com.example.mygyp.R.style.AppTheme_users)
    }

    private fun getThemePreference(): Boolean {
        return getSharedPreferences(THEME_PREF, MODE_PRIVATE).getBoolean(IS_DARK_THEME, false)
    }

    private fun saveThemePreference(isDarkTheme: Boolean) {
        getSharedPreferences(THEME_PREF, MODE_PRIVATE).edit().apply {
            putBoolean(IS_DARK_THEME, isDarkTheme)
            apply()
        }
    }
}

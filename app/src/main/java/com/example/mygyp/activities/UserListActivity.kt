package com.example.mygyp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygyp.R
import com.example.mygyp.adapters.UserAdapter
import com.example.mygyp.adapters.UserListener
import com.example.mygyp.databinding.ActivityPlacemarkListBinding
import com.example.mygyp.main.MainApp
import com.example.mygyp.models.UserModel
import com.google.android.material.snackbar.Snackbar

class UserListActivity : AppCompatActivity(), UserListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and calling
     * [AppCompatActivity.setSupportActionBar] to set up the app bar.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in [AppCompatActivity.onSaveInstanceState].
     *     Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = UserAdapter(app.users.findAll(), this)

        binding.topAppBar.title = title // Name of the Project
        setSupportActionBar(binding.topAppBar)
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     *     if you return false it will not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_maion, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handle menu item clicks.
     *
     * @param item The menu item that was selected.
     * @return true if the menu item was handled successfully, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_menu -> {
                // Start the Settings Activity
                startActivity(Intent(this, SettingActivitvy::class.java))
                true
            }
            R.id.item_add -> {
                // Start an activity or perform another action when the add button is pressed
                // For example, start an Add User Activity
                startActivity(Intent(this, UserActivity::class.java))
                true
            }
            else -> {
                // This ensures that any unexpected menu items are handled appropriately
                super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Activity result launcher for handling results from starting activities for result.
     * This will be triggered when the activity launched by [registerForActivityResult]
     * finishes and returns a result.
     */


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                    0,
                    app.users.findAll().size,
                )
            }
            if (it.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.enter_User_title),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }

    /**
     * Handles the click event when a user item is clicked.
     *
     * @param user The user model associated with the clicked item.
     */

    override fun onuserClick(user: UserModel) {
        val launcherIntent = Intent(this, UserActivity::class.java)
        launcherIntent.putExtra("placemark_edit", user)
        getResult.launch(launcherIntent)
    }

    override fun onUserClick(user: UserModel) {
    }

    override fun removeItem(user: UserModel) {
    }
}

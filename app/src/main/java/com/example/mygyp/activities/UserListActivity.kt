package com.example.mygyp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = UserAdapter(app.users.findAll(), this)

        binding.topAppBar.title = title // Name of the Project
        setSupportActionBar(binding.topAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_maion, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, UserActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

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

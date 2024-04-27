package com.example.mygyp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mygyp.R
import com.example.mygyp.databinding.ActivityPlacemarkBinding
import com.example.mygyp.helpers.showImagePicker
import com.example.mygyp.main.MainApp
import com.example.mygyp.models.Location
import com.example.mygyp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    var user = UserModel()
    lateinit var app: MainApp
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.title = title
        setSupportActionBar(binding.topAppBar)

        app = application as MainApp
        var edit = false

        i(getString(R.string.User_activity_started))

        binding.deletebutton.setOnClickListener {
            val confirmationDialog =
                AlertDialog.Builder(this)
                    .setTitle("Delete User")
                    .setMessage("do u want to delete this ${user.id}?")
                    .setPositiveButton("Delete") { _, _ ->
                        app.users.delete(user.copy())
                        finish()
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
            confirmationDialog.show()
        }

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            user = intent.extras?.getParcelable("placemark_edit")!!
            binding.placemarkTitle.setText(user.firstname)
            binding.placemarkDescription.setText(user.lastname)
            binding.btnAdd.text = getString(R.string.save_User)
            Picasso.get()
                .load(user.image)
                .into(binding.placemarkImage)

            if (user.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_User_image)
            }
        }

        binding.btnAdd.setOnClickListener {
            user.firstname = binding.placemarkTitle.text.toString()
            user.lastname = binding.placemarkDescription.text.toString()
            if (user.firstname.isNotEmpty()) {
                if (edit) {
                    app.users.update(user.copy())
                } else {
                    app.users.create(user.copy())
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(
                    it,
                    getString(R.string.enter_User_title),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        binding.placemarkLocation.setOnClickListener {
            val launcherIntent =
                Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            user.image = result.data!!.data!!
                            Picasso.get()
                                .load(user.image)
                                .into(binding.placemarkImage)
                            binding.chooseImage.setText(R.string.change_User_image)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}

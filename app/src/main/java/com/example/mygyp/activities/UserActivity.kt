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

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and calling
     * [AppCompatActivity.setSupportActionBar] to set up the app bar.
     *
     * This method initializes the activity's UI by inflating the layout, setting up the app bar,
     * retrieving necessary data from the intent extras, and initializing the application instance.
     * It also sets up a click listener for the delete button to prompt the user for confirmation
     * before deleting the user.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in [AppCompatActivity.onSaveInstanceState].
     *     Note: Otherwise, it is null.
     */
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
            showImagePicker(imageIntentLauncher, this)
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

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * This method inflates the menu resource file (menu_placemark) to populate the options menu
     * in the app bar.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handle menu item clicks.
     *
     * This method is called when a menu item in the options menu is selected.
     * It handles the action for the "Cancel" menu item by setting the result code to
     * RESULT_CANCELED and finishing the activity.
     *
     * @param item The menu item that was selected.
     * @return true if the menu item was handled successfully, false otherwise.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Registers the callback for handling image picker results.
     *
     * This method registers an activity result callback using ActivityResultContracts.StartActivityForResult
     * to handle the result of the image picker activity. It updates the user's image based on the selected
     * image URI and updates the UI accordingly.
     */
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            Picasso.get().load(user.image).into(binding.placemarkImage)
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

    /**
     * Registers the callback for handling map activity results.
     *
     * This method registers an activity result callback using ActivityResultContracts.StartActivityForResult
     * to handle the result of the map activity. It updates the location data based on the selected location
     * and updates the UI accordingly.
     */
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

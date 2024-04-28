package com.example.mygyp.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.mygyp.R
/**
 * Launches an image picker activity to select an image.
 *
 * @param intentLauncher The activity result launcher for launching the image picker activity.
 */
fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_User_image.toString())
    intentLauncher.launch(chooseFile)
}

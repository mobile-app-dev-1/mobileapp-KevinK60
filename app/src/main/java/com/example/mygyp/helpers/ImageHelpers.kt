package com.example.mygyp.helpers

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.mygyp.R

/**
 * Launches an image picker activity to select an image.
 *
 * @param intentLauncher The activity result launcher for launching the image picker activity.
 */
fun showImagePicker(
    intentLauncher: ActivityResultLauncher<Intent>,
    context: Context,
) {
    var imagePickerTargetIntent = Intent()

    imagePickerTargetIntent.action = Intent.ACTION_OPEN_DOCUMENT
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    imagePickerTargetIntent.type = "image/*"
    imagePickerTargetIntent =
        Intent.createChooser(
            imagePickerTargetIntent,
            context.getString(R.string.select_User_image),
        )
    intentLauncher.launch(imagePickerTargetIntent)
}

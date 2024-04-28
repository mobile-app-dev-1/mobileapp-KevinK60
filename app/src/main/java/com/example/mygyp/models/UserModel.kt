package com.example.mygyp.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a user model.
 *
 * @property id The unique identifier of the user.
 * @property firstname The first name of the user.
 * @property lastname The last name of the user.
 * @property image The image URI of the user.
 */
@Parcelize
data class UserModel(
    var id: Long = 0,
    var firstname: String = "",
    var lastname: String = "",
    var image: Uri? = Uri.EMPTY,
) : Parcelable

/**
 * Represents a location with latitude, longitude, and zoom level.
 *
 * @property lat The latitude coordinate of the location.
 * @property lng The longitude coordinate of the location.
 * @property zoom The zoom level of the location.
 */
@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
) : Parcelable

/**
 * Represents a day of the week.
 *
 * @property name The name of the day.
 */
data class Day(
    val name: String,
)

/**
 * Represents an hour of the day.
 *
 * @property hour The hour value.
 */
data class hour(
    val hour: Int,
)

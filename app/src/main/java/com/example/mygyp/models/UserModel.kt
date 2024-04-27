package com.example.mygyp.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: Long = 0,
    var firstname: String = "",
    var lastname: String = "",
    var image: Uri = Uri.EMPTY,
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
) : Parcelable

data class Day(
    val name: String,
)

data class hour(
    val hour: Int,
)

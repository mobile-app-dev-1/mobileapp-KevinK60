package com.example.myapplication.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Long = 0,
    var firstname: String = "",
    var lastname: String = "",
    var image: Uri = Uri.EMPTY
) : Parcelable


@kotlinx.parcelize.Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
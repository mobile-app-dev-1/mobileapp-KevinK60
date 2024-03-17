package com.example.mygyp.models

import timber.log.Timber.i

var lastId = 0L
internal fun getId() = lastId++

class PlacemarkMemStore : UserStore {

    val users  = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)
        logAll()
    }

    override fun update(placemark: UserModel) {
        var foundPlacemark: UserModel? = users.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.firstname = placemark.firstname
            foundPlacemark.lastname = placemark.lastname
            foundPlacemark.image = placemark.image
            logAll()
        }
    }

    private fun logAll() {
        users.forEach { i("$it") }
    }
}
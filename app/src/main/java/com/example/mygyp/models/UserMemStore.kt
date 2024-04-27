package com.example.mygyp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class UserMemStore : UserStore {
    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        val foundUser: UserModel? = users.find { it.id == user.id }
        if (foundUser != null) {
            foundUser.firstname = user.firstname
            foundUser.lastname = user.lastname
            foundUser.image = user.image
            logAll()
        }
    }

    override fun delete(user: UserModel) {
        users.remove(user)
    }

    fun deletebyid(userId: Long) {
        val userToRemove = users.find { it.id == userId }
        if (userToRemove != null) {
            users.remove(userToRemove)
            logAll()
        } else {
            i("User with ID $userId not found.")
        }
    }

    private fun logAll() {
        users.forEach { i("$it") }
    }
}

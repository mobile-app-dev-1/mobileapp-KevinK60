package com.example.myapplication.models
import timber.log.Timber.i
var lastId = 0L
internal fun getId() = lastId++
class userMemStore: UsersStore {

    val users = ArrayList<UserModel>()
    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)

    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.firstname = user.firstname
            foundUser.lastname = user.lastname
            foundUser.image = user.image
            logAll()

        }
    }
        private fun logAll() {
            users.forEach { i("$it") }
        }
    }

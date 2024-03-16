package com.example.myapplication.models
import timber.log.Timber.i
class userMemStore: UsersStore {
    val users = ArrayList<UsersStore>()

    override fun findAll(): List<UsersStore> {
        return users
    }
    override fun create(user: UsersStore){
        users.add(user)
    }
}
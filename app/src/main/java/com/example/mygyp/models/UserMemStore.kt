package com.example.mygyp.models

import timber.log.Timber.i

var lastId = 0L

/**
 * Retrieves the next available unique identifier and increments the last used identifier.
 *
 * @return The next available unique identifier.
 */
internal fun getId(): Long {
    return lastId++
}

/**
 * Memory-based implementation of the [UserStore] interface.
 * This class stores user data in memory using an ArrayList.
 */
class UserMemStore : UserStore {
    val users = ArrayList<UserModel>()

    /**
     * Retrieves all user models stored in memory.
     *
     * @return A list of all user models stored in memory.
     */
    override fun findAll(): List<UserModel> {
        return users
    }

    /**
     * Creates a new user and adds it to the memory storage.
     *
     * @param user The user model to be created.
     */
    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)
        logAll()
    }

    /**
     * Updates an existing user with new data.
     *
     * @param user The updated user model containing the new data.
     */
    override fun update(user: UserModel) {
        val foundUser: UserModel? = users.find { it.id == user.id }
        if (foundUser != null) {
            foundUser.firstname = user.firstname
            foundUser.lastname = user.lastname
            foundUser.image = user.image
            logAll()
        }
    }

    /**
     * Deletes the specified user from the memory storage.
     *
     * @param user The user model to be deleted.
     */
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

    override fun findById(id: Long): UserModel? {
        val foundUser: UserModel? = users.find { it.id == id }
        return foundUser
    }

    private fun logAll() {
        users.forEach { i("$it") }
    }
}

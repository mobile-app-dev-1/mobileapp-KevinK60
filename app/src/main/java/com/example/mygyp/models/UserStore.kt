package com.example.mygyp.models

interface UserStore {
    suspend fun findAll(): List<UserModel>
    fun findById(id: Long): UserModel?
    fun create(user: UserModel)
    suspend fun update(user: UserModel)
    fun delete(user: UserModel)

}

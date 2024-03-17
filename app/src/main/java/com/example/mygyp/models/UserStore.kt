package com.example.mygyp.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
}
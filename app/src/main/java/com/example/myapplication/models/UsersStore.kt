package com.example.myapplication.models

interface UsersStore {
    fun findAll(): List<UsersStore>
    fun create(user: UsersStore)
    fun update(user: UsersStore)
}
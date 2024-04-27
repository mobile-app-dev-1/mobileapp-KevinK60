package com.example.mygyp.models

import android.content.Context
import android.net.Uri
import com.example.mygyp.helpers.*
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "users.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UserJSONStore.UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type
fun generateRandomId(): Long {
    return Random().nextLong()
}
class UserJSONStore(private val context: Context) : UserStore {

    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override suspend fun findAll(): MutableList<UserModel> {
        logAll()
        return users
    }

    override fun findById(id: Long) : UserModel? {
        val foundUser: UserModel? = users.find { it.id == id }
        return foundUser
    }

    override fun create(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override suspend fun update(user: UserModel) {
        val usersList = findAll() as ArrayList<UserModel>
        val foundUser: UserModel? = usersList.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.firstname = user.firstname
            foundUser.lastname = user.lastname
            foundUser.image = user.image

        }
        serialize()
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)
    }
    class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Uri {
            return Uri.parse(json?.asString)
        }

        override fun serialize(
            src: Uri?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.toString())
        }
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}
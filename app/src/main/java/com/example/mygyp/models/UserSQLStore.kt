package com.example.mygyp.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.net.toUri
import timber.log.Timber

private const val DATABASE_NAME = "users.db"
private const val TABLE_NAME = "users"
private const val COLUMN_ID = "id"
private const val COLUMN_TITLE = "title"
private const val COLUMN_DESCRIPTION = "description"
private const val COLUMN_IMAGE = "image"

class UserSQLStore(private val context: Context) : UserStore {
    private var database: SQLiteDatabase

    init {
        // Set up local database connection
        database = UserDbHelper(context).writableDatabase
    }

    override fun findAll(): List<UserModel> {
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        val users = mutableListOf<UserModel>() // Renamed from placemarks to users for clarity

        cursor.use { cur -> // Rename it to avoid confusion with outer cursor variable
            val idIndex = cur.getColumnIndexOrThrow(COLUMN_ID)
            val titleIndex = cur.getColumnIndexOrThrow(COLUMN_TITLE)
            val descriptionIndex = cur.getColumnIndexOrThrow(COLUMN_DESCRIPTION)
            val imageIndex = cur.getColumnIndexOrThrow(COLUMN_IMAGE)

            while (cur.moveToNext()) {
                val user =
                    UserModel(
                        id = cur.getLong(idIndex),
                        firstname = cur.getString(titleIndex), // Assuming COLUMN_TITLE maps to firstname
                        lastname = cur.getString(descriptionIndex), // Assuming COLUMN_DESCRIPTION maps to lastname
                        image = cur.getString(imageIndex).toUri(),
                    )
                users.add(user)
            }
        }

        Timber.i("Database : findAll() - has ${users.size} records")
        return users
    }

    override fun create(user: UserModel) {
        val contentValues =
            ContentValues().apply {
                put(COLUMN_TITLE, user.firstname)
                put(COLUMN_DESCRIPTION, user.lastname)
                put(COLUMN_IMAGE, user.image.toString())
            }
        try {
            database.insertOrThrow(TABLE_NAME, null, contentValues)
            Timber.i("User created successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to create user")
        }
    }

    override fun findById(id: Long): UserModel? {
        val cursor =
            database.query(
                TABLE_NAME,
                arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_IMAGE),
                "$COLUMN_ID = ?",
                arrayOf(id.toString()),
                null,
                null,
                null,
            )
        cursor.use { cur ->
            if (cur.moveToFirst()) {
                return UserModel(
                    id = cur.getLong(cur.getColumnIndexOrThrow(COLUMN_ID)),
                    firstname = cur.getString(cur.getColumnIndexOrThrow(COLUMN_TITLE)),
                    lastname = cur.getString(cur.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    image = cur.getString(cur.getColumnIndexOrThrow(COLUMN_IMAGE)).toUri(),
                )
            }
        }
        return null
    }

    override fun update(user: UserModel) {
        val contentValues =
            ContentValues().apply {
                put(COLUMN_TITLE, user.firstname)
                put(COLUMN_DESCRIPTION, user.lastname)
                put(COLUMN_IMAGE, user.image.toString())
            }
        try {
            database.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
            Timber.i("User updated successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to update user")
        }
    }

    override fun delete(user: UserModel) {
        try {
            database.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(user.id.toString()))
            Timber.i("User deleted successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to delete user")
        }
    }
}

private class UserDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    private val createTableSQL = """
    CREATE TABLE $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_TITLE TEXT,
        $COLUMN_DESCRIPTION TEXT,
        $COLUMN_IMAGE TEXT
    )
"""

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }
}

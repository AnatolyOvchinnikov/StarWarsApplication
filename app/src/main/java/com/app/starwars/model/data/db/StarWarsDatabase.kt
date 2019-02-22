package com.app.starwars.model.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.app.starwars.entity.User
import com.app.starwars.entity.UserMetadata

@Database(
    entities = [User::class, UserMetadata::class],
    version = 1,
    exportSchema = false
)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun usersDao(): UserDao
}
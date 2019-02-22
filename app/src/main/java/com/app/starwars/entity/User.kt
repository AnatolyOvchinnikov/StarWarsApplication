package com.app.starwars.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_metadata")
@Parcelize
data class UserMetadata(
    @PrimaryKey val userId: Long,
    val visited: Boolean
) : Parcelable

@Entity(tableName = "user")
@Parcelize
data class User(
        val name: String,
        val height: String,
        val mass: String,
        val hair_color: String,
        val skin_color: String,
        val eye_color: String,
        val birth_year: String,
        val gender: String,
        val created: String,
        val edited: String,
        var url: String
) : Parcelable {
        @PrimaryKey var userId: Long? = 0
                get() {
                        return Uri.parse(url)?.lastPathSegment?.toLong()
                }
}

data class UserWithMetadata(
        @Embedded val user: User,
        val visited: Boolean
)

@Parcelize
data class UsersListResponce(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<User>
) : Parcelable
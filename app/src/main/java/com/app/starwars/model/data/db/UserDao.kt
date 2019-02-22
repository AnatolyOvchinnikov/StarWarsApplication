package com.app.starwars.model.data.db

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.app.starwars.entity.User
import com.app.starwars.entity.UserMetadata
import com.app.starwars.entity.UserWithMetadata

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<User>)

    @Query("SELECT user.*, user_metadata.visited FROM user LEFT JOIN user_metadata ON user.userId = user_metadata.userId WHERE (user.name LIKE :queryString) " + "ORDER BY user.userId ASC")
    fun usersByName(queryString: String): DataSource.Factory<Int,UserWithMetadata>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: User): Int

    @Query("SELECT * FROM user_metadata")
    fun selectUserMetadata(): List<UserMetadata>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateMetadata(userMetadata: UserMetadata)
}
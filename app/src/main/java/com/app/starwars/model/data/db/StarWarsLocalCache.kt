package com.app.starwars.model.data.db

import android.arch.paging.DataSource
import android.util.Log
import com.app.starwars.entity.User
import com.app.starwars.entity.UserMetadata
import com.app.starwars.entity.UserWithMetadata
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class StarWarsLocalCache @Inject constructor(val usersDao: UserDao,
                                             val ioExecutor: Executor
) {
    fun insert(users: List<User>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", "inserting ${users.size} users")
            usersDao.insert(users)
            insertFinished()
        }
    }

    fun usersByName(name: String): DataSource.Factory<Int, UserWithMetadata> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${name.replace(' ', '%')}%"
        return usersDao.usersByName(query)
    }

    fun updateuserMetadata(userMetadata: UserMetadata) {
        ioExecutor.execute {
            usersDao.updateMetadata(userMetadata)
        }
    }
}
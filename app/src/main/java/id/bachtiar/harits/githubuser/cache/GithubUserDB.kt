package id.bachtiar.harits.githubuser.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import id.bachtiar.harits.githubuser.cache.dao.UsersDao
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity

@Database(
    entities = [UsersEntity::class],
    version = 1
)
abstract class GithubUserDB : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}
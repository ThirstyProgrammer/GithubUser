package id.bachtiar.harits.githubuser.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.bachtiar.harits.githubuser.cache.dao.UsersDao
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity

@Database(entities = [UsersEntity::class], version = 1, exportSchema = false)
abstract class GithubUserDB :RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {

        const val KARIERMU_DB = "db_github_user"

        @Volatile
        private var INSTANCE: GithubUserDB? = null

        fun getDatabase(
            context: Context,
        ): GithubUserDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubUserDB::class.java,
                    KARIERMU_DB
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
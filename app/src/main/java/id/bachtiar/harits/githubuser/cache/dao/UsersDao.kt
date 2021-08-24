package id.bachtiar.harits.githubuser.cache.dao

import androidx.room.*
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity

@Dao
interface UsersDao {

    @Query("Select * from users")
    suspend fun getUsers(): List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usersEntity: UsersEntity)

    @Update
    suspend fun update(usersEntity: UsersEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
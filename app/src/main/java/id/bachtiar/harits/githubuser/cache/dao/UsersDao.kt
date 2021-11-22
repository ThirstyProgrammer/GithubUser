package id.bachtiar.harits.githubuser.cache.dao

import androidx.room.*
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("Select * from users")
    fun getUsers(): Flow<List<UsersEntity>>

    @Query("Select * from users WHERE username LIKE :search")
    fun searchUsers(search: String?): Flow<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usersEntity: UsersEntity)

    @Update
    suspend fun update(usersEntity: UsersEntity)

    @Delete
    suspend fun delete(usersEntity: UsersEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
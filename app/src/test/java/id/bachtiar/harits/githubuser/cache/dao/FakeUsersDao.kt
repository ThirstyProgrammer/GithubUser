package id.bachtiar.harits.githubuser.cache.dao

import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUsersDao : UsersDao {

    override fun getUsers(): Flow<List<UsersEntity>> {
        return flowOf(arrayListOf())
    }

    override fun searchUsers(search: String?): Flow<List<UsersEntity>> {
        return flowOf(arrayListOf())
    }

    override suspend fun insert(usersEntity: UsersEntity) {}

    override suspend fun update(usersEntity: UsersEntity) {}

    override suspend fun delete(usersEntity: UsersEntity) {}

    override suspend fun deleteAll() {}
}
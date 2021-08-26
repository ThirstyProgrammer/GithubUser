package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.model.User

interface UsersRepository {

    suspend fun getUsers(): List<User>

    suspend fun getSearchUsers(username: String): List<User>

    suspend fun getUserDetail(url: String): User

    suspend fun getUsersByURL(url: String, page: Int): List<User>
}
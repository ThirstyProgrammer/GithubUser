package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.model.User

class FakeGithubUserRepository : UsersRepository {

    private val users = mutableListOf<User>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getUsers(): List<User> {
        users.add(User(1, "bachtiar"))
        return users
    }

    override suspend fun getSearchUsers(username: String): List<User> {
        users.add(User(1, "bachtiar"))
        return users.filter { it.username!!.contains(username) }
    }

    override suspend fun getUserDetail(url: String): User {
        return users[0]
    }

    override suspend fun getUsersByURL(url: String, page: Int): List<User> {
        return users
    }


}
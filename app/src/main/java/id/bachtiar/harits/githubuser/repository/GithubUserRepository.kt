package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.ApiService
import id.bachtiar.harits.githubuser.util.Constant
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GithubUserRepository @Inject constructor(private val service: ApiService) : UsersRepository {

    override suspend fun getUsers(): List<User> = service.getUsers(Constant.API_KEY)

    override suspend fun getSearchUsers(
        username: String
    ): List<User> = service.getSearchUsers(Constant.API_KEY, username).items

    override suspend fun getUserDetail(
        url: String
    ): User = service.getUserDetail(Constant.API_KEY, url)

    override suspend fun getUsersByURL(
        url: String,
        page: Int
    ): List<User> = service.getUsersByURL(Constant.API_KEY, "$url?page=$page&per_page=12")
}
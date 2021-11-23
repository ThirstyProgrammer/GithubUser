package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.BuildConfig
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.ApiService
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GithubUserRepository @Inject constructor(private val service: ApiService) : UsersRepository {

    override suspend fun getUsers(): List<User> = service.getUsers(BuildConfig.GITHUB_TOKEN)

    override suspend fun getSearchUsers(
        username: String
    ): List<User> = service.getSearchUsers(BuildConfig.GITHUB_TOKEN, username).items

    override suspend fun getUserDetail(
        url: String
    ): User = service.getUserDetail(BuildConfig.GITHUB_TOKEN, url)

    override suspend fun getUsersByURL(
        url: String,
        page: Int
    ): List<User> = service.getUsersByURL(BuildConfig.GITHUB_TOKEN, "$url?page=$page&per_page=12")
}
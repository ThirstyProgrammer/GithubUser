package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.network.RetrofitFactory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class GithubUserRepository {

    private val service = RetrofitFactory.makeRetrofitService()

    suspend fun getUsers() = service.getUsers(Constant.API_KEY)

    suspend fun getSearchUsers(username: String) = service.getSearchUsers(Constant.API_KEY, username).items

    suspend fun getUserDetail(url: String) = service.getUserDetail(Constant.API_KEY, url)

    suspend fun getUsersByURL(url: String, page: Int) =
        service.getUsersByURL(Constant.API_KEY, "$url?page=$page&per_page=12")
}
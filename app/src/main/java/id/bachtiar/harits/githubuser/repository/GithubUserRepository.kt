package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.network.RetrofitFactory
import kotlinx.serialization.ExperimentalSerializationApi

class GithubUserRepository {

    @ExperimentalSerializationApi
    private val service = RetrofitFactory.makeRetrofitService()

    @ExperimentalSerializationApi
    suspend fun getUsers() = service.getUsers(Constant.API_KEY)

    @ExperimentalSerializationApi
    suspend fun getUserDetail(url: String) = service.getUserDetail(Constant.API_KEY, url)

    @ExperimentalSerializationApi
    suspend fun getUsersByURL(url: String, page: Int) =
        service.getUsersByURL(Constant.API_KEY, "$url?page=$page&per_page=12")
}
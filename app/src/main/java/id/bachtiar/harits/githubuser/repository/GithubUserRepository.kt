package id.bachtiar.harits.githubuser.repository

import id.bachtiar.harits.githubuser.Constant
import id.bachtiar.harits.githubuser.network.RetrofitFactory

class GithubUserRepository {

    private val service = RetrofitFactory.makeRetrofitService()

    suspend fun getUsers() = service.getUsers(Constant.API_KEY)
}
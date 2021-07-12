package id.bachtiar.harits.githubuser.network

import id.bachtiar.harits.githubuser.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    @GET("users")
    suspend fun getUsers(
        @Header(AUTHORIZATION) value: String,
    ): List<User>
}
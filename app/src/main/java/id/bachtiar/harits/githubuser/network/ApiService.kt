package id.bachtiar.harits.githubuser.network

import id.bachtiar.harits.githubuser.model.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiService {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    @GET("users")
    suspend fun getUsers(
        @Header(AUTHORIZATION) value: String,
    ): List<User>

    @GET
    suspend fun getUserDetail(
        @Header(AUTHORIZATION) value: String,
        @Url url: String
    ): User

    @GET
    suspend fun getUsersByURL(
        @Header(AUTHORIZATION) value: String,
        @Url url: String
    ): List<User>
}
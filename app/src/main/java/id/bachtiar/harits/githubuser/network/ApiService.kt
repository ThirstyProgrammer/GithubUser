package id.bachtiar.harits.githubuser.network

import id.bachtiar.harits.githubuser.model.SearchResult
import id.bachtiar.harits.githubuser.model.User
import retrofit2.http.*

interface ApiService {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    @GET("users")
    suspend fun getUsers(
        @Header(AUTHORIZATION) value: String,
    ): List<User>

    @GET("search/users")
    suspend fun getSearchUsers(
        @Header(AUTHORIZATION) value: String,
        @Query("q") username:String
    ): SearchResult

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
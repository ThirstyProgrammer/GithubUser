package id.bachtiar.harits.githubuser.model

import android.os.Parcelable
import id.bachtiar.harits.githubuser.UserAdapter
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import id.bachtiar.harits.githubuser.util.defaultEmpty
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Parcelize
@Serializable
data class User(
    val id: Int? = 0,
    @SerialName("login") val username: String? = "",
    @SerialName("avatar_url") val avatar: String? = "",
    val name: String? = "",
    val company: String? = "",
    val location: String? = "",
    val followers: Int? = 0,
    val following: Int? = 0,
    @SerialName("public_repos") val repos: Int? = 0,
    val url: String? = "",
    @SerialName("html_url") val githubUrl: String? = "",
    @SerialName("followers_url") val followersUrl: String? = "",
    @SerialName("following_url") val followingUrl: String? = "",

    @Transient
    var isFavourite: Boolean = false,
    @Transient
    var typeItem: Int = UserAdapter.TYPE_ITEM
) : Parcelable {

    fun mapToUserEntity(): UsersEntity {
        return UsersEntity(
            id = id ?: 0,
            username = username.defaultEmpty(),
            avatar = avatar.defaultEmpty(),
            githubUrl = githubUrl.defaultEmpty(),
            url = url.defaultEmpty()
        )
    }
}
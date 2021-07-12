package id.bachtiar.harits.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
    @SerialName("login")
    val username: String = "",
    val id : Int = 0,
    val avatar_url: String = "",
    val url: String = "",
    val followers_url: String = "",
    val following_url: String = ""
) : Parcelable
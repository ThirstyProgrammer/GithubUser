package id.bachtiar.harits.githubuser.cache.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.bachtiar.harits.githubuser.model.User

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "username") val username: String,
    @NonNull @ColumnInfo(name = "avatar") val avatar: String,
    @NonNull @ColumnInfo(name = "github_url") val githubUrl: String,
    @NonNull @ColumnInfo(name = "url") val url: String
) {

    fun mapToModel(): User {
        return User(
            id = this.id,
            username = this.username,
            avatar = this.avatar,
            githubUrl = this.githubUrl,
            url = this.url
        )
    }
}
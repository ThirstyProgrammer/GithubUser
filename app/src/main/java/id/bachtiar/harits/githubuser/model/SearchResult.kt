package id.bachtiar.harits.githubuser.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val items: List<User>
)
package id.bachtiar.harits.githubuser.ui.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import id.bachtiar.harits.githubuser.repository.GithubUserRepository
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class FollowersViewModel @Inject constructor(private val repo: GithubUserRepository) : BaseViewModel() {

    var updatedFollowers: ArrayList<User> = arrayListOf()
    var isLastPage: Boolean = false
    var url: String = ""
    private var page: Int = 1

    private val _followers = MutableLiveData<List<User>>()
    val followers: LiveData<List<User>> = _followers

    @ExperimentalSerializationApi
    fun getFollowers(isLoadMore: Boolean = false) {
        if (isLoadMore) page += 1
        requestAPI(_followers, NetworkRequestType.LIST_USERS, !isLoadMore) {
            repo.getUsersByURL(url, page)
        }
    }
}
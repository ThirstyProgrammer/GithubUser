package id.bachtiar.harits.githubuser.ui.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import kotlinx.serialization.ExperimentalSerializationApi

class FollowersViewModel : BaseViewModel() {

    private val _followers = MutableLiveData<List<User>>()
    val followers: LiveData<List<User>> = _followers

    var isLastPage: Boolean = false
    var url: String = ""
    private var page: Int = 1

    @ExperimentalSerializationApi
    fun getFollowers(isLoadMore: Boolean = false) {
        if (isLoadMore) page += 1
        requestAPI(_followers, !isLoadMore) {
            repo.getUsersByURL(url, page)
        }
    }
}
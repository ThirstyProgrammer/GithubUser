package id.bachtiar.harits.githubuser.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import kotlinx.serialization.ExperimentalSerializationApi

class FollowingViewModel : BaseViewModel() {

    var isLastPage: Boolean = false
    var url: String = ""
    private var page: Int = 1

    private val _following = MutableLiveData<List<User>>()
    val following: LiveData<List<User>> = _following

    @ExperimentalSerializationApi
    fun getFollowing(isLoadMore: Boolean = false) {
        if (isLoadMore) page += 1
        requestAPI(_following, !isLoadMore) {
            repo.getUsersByURL(url, page)
        }
    }
}
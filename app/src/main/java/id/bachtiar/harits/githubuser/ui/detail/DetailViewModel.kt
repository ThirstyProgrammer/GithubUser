package id.bachtiar.harits.githubuser.ui.detail

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
class DetailViewModel @Inject constructor(private val repo: GithubUserRepository) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    @ExperimentalSerializationApi
    fun getUserDetail(url: String) {
        requestAPI(_user, NetworkRequestType.USER_DETAIL) {
            repo.getUserDetail(url)
        }
    }
}
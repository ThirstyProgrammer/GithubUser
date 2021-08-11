package id.bachtiar.harits.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import kotlinx.serialization.ExperimentalSerializationApi

class DetailViewModel : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    @ExperimentalSerializationApi
    fun getUserDetail(url: String) {
        requestAPI(_user, NetworkRequestType.USER_DETAIL) {
            repo.getUserDetail(url)
        }
    }
}
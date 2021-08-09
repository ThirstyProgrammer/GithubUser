package id.bachtiar.harits.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import kotlinx.serialization.ExperimentalSerializationApi

class DetailViewModel : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    @ExperimentalSerializationApi
    fun getUserDetail(url: String) {
        requestAPI(_user) {
            repo.getUserDetail(url)
        }
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                requestAPI(_user, repo.getUserDetail(url))
//                try {
//                    val result = repo.getUserDetail(url)
//                    _user.postValue(result)
//                } catch (throwable: Throwable) {
//                    handleNetworkError(NetworkRequestType.USER_DETAIL, throwable)
//                }
//            }
//        }
    }
}
package id.bachtiar.harits.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import kotlinx.serialization.ExperimentalSerializationApi

class MainViewModel : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    var username: String = ""

    @ExperimentalSerializationApi
    fun getUsers() {
        if (username.isEmpty()){
            requestAPI(_users) {
                repo.getUsers()
            }
        }else{
            requestAPI(_users) {
                repo.getSearchUsers(username)
            }
        }
    }
}
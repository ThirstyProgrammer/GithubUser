package id.bachtiar.harits.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.cache.dao.UsersDao
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import id.bachtiar.harits.githubuser.repository.UsersRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: UsersRepository,
    private val dao: UsersDao
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    val favouriteUsers: LiveData<List<UsersEntity>> = dao.getUsers().asLiveData()
    var username: String = ""

    @ExperimentalSerializationApi
    fun getUsers() {
        if (username.isEmpty()) {
            requestAPI(_users, NetworkRequestType.LIST_USERS) {
                repo.getUsers()
            }
        } else {
            requestAPI(_users, NetworkRequestType.LIST_USERS) {
                repo.getSearchUsers(username)
            }
        }
    }

    fun addUserToFavourite(user: User) = viewModelScope.launch {
        dao.insert(user.mapToUserEntity())
    }

    fun deleteUserFromFavourite(user: User) = viewModelScope.launch {
        dao.delete(user.mapToUserEntity())
    }

    fun generateList(): List<User> {
        val newList = arrayListOf<User>()
        users.value?.map {
            it.isFavourite = checkIsFavourite(it)
            newList.add(it)
        }
        return newList
    }

    fun checkIsFavourite(user: User): Boolean {
        favouriteUsers.value?.forEach {
            if (user.id == it.id) return true
        }
        return false
    }
}
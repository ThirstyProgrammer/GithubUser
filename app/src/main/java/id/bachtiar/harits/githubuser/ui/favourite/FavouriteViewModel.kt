package id.bachtiar.harits.githubuser.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.cache.dao.UsersDao
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import id.bachtiar.harits.githubuser.model.User
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dao: UsersDao
) : BaseViewModel() {

    var username: String = ""

    fun deleteUserFromFavourite(user: User) = viewModelScope.launch {
        dao.delete(user.mapToUserEntity())
    }

    fun getSearchUsers(): LiveData<List<UsersEntity>> =
        dao.searchUsers(username).asLiveData()

    fun getUsers(): LiveData<List<UsersEntity>> = dao.getUsers().asLiveData()
}
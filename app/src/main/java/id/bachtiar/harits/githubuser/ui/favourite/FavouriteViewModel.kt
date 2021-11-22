package id.bachtiar.harits.githubuser.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.cache.dao.UsersDao
import id.bachtiar.harits.githubuser.cache.entity.UsersEntity
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val dao: UsersDao
) : BaseViewModel() {

    var username: String = ""
    val users: LiveData<List<UsersEntity>> = dao.getUsers().asLiveData()
    val searchUsers: LiveData<List<UsersEntity>> = dao.searchUsers(username).asLiveData()
}
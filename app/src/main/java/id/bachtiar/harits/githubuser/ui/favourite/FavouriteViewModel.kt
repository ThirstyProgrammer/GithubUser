package id.bachtiar.harits.githubuser.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bachtiar.harits.githubuser.base.BaseViewModel
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.repository.UsersRepository
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repo: UsersRepository) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    var username: String = ""

    fun getUsers() {
        // TODO Handle get users from room
    }
}
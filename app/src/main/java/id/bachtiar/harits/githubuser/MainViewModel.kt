package id.bachtiar.harits.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    private var repo = GithubUserRepository()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = repo.getUsers()
                    _users.postValue(result)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            _error.postValue("Network Error")
                        }
                        is HttpException -> {
                            val code = throwable.code()
                            val errorResponse = throwable.message()
                            _error.postValue("Error $code $errorResponse")
                        }
                        else -> {
                            _error.postValue("Unkown Error")
                        }
                    }
                }
            }
            val result = withContext(Dispatchers.IO) {
                repo.getUsers()
            }
            _users.postValue(result)
        }
    }

//    fun setUsers() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = service.getUser(Constant.API_KEY)
//            withContext(Dispatchers.Main) {
//                try {
//                    if (response.isSuccessful) {
//                        // Do something with ui
//                        Log.d("Success", response.body().toString())
//                    } else {
//                        Log.d("Exception", response.code().toString())
//                    }
//                } catch (e: HttpException) {
//                    Log.d("Exception", e.message.toString())
//                } catch (e: Throwable) {
//                    Log.d("Throwable", "Something else went wrong")
//                }
//            }
//        }
//    }
//
//    fun getUsers(): LiveData<ArrayList<User>> {
//        return users
//    }
}
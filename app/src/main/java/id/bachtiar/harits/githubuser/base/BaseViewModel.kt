package id.bachtiar.harits.githubuser.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import id.bachtiar.harits.githubuser.network.ViewState
import id.bachtiar.harits.githubuser.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseViewModel : ViewModel() {

    val repo = GithubUserRepository()

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private val _error = MutableLiveData<Pair<NetworkRequestType, String>>()
    val error: LiveData<Pair<NetworkRequestType, String>> = _error

    fun <T> requestAPI(data: MutableLiveData<T>, viewStateActive: Boolean = true, apiMethod: suspend () -> T) {
        if (viewStateActive) _viewState.value = ViewState.LOADING
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = apiMethod()
                    data.postValue(result)
                    if (viewStateActive) _viewState.postValue(ViewState.SUCCESS)
                } catch (throwable: Throwable) {
                    handleNetworkError(NetworkRequestType.USER_DETAIL, throwable)
                    if (viewStateActive) _viewState.postValue(ViewState.ERROR)
                }
            }
        }
    }

    private fun handleNetworkError(requestType: NetworkRequestType, throwable: Throwable) {
        val message : String = when (throwable) {
            is IOException -> {
                "Network Error"
            }
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = throwable.message()
                "Error $code $errorResponse"
            }
            else -> {
                "Unknown Error"
            }
        }
        _error.postValue(Pair(requestType, message))
    }
}
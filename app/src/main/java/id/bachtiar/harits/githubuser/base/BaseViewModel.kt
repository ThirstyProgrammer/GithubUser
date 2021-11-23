package id.bachtiar.harits.githubuser.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import id.bachtiar.harits.githubuser.network.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalSerializationApi
abstract class BaseViewModel : ViewModel() {

    private val _viewState = MutableLiveData<Pair<ViewState, NetworkRequestType>>()
    val viewState: LiveData<Pair<ViewState, NetworkRequestType>> = _viewState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun updateViewState(state: ViewState, networkRequestType: NetworkRequestType) {
        _viewState.postValue(Pair(state, networkRequestType))
    }

    fun <T> requestAPI(
        data: MutableLiveData<T>,
        requestType: NetworkRequestType,
        viewStateActive: Boolean = true,
        apiMethod: suspend () -> T
    ) {
        viewModelScope.launch {
            if (viewStateActive) _viewState.postValue(Pair(ViewState.LOADING, requestType))
            withContext(Dispatchers.IO) {
                try {
                    val result = apiMethod()
                    data.postValue(result)
                    if (viewStateActive) _viewState.postValue(Pair(ViewState.SUCCESS, requestType))
                } catch (throwable: Throwable) {
                    handleNetworkError(throwable)
                    if (viewStateActive) _viewState.postValue(Pair(ViewState.ERROR, requestType))
                }
            }
        }
    }

    private fun handleNetworkError(throwable: Throwable) {
        val message: String = when (throwable) {
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
        _error.postValue(message)
    }
}
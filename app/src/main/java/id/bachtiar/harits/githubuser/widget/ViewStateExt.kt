package id.bachtiar.harits.githubuser.widget

import android.view.View
import id.bachtiar.harits.githubuser.databinding.ViewStateBinding
import id.bachtiar.harits.githubuser.network.NetworkRequestType
import id.bachtiar.harits.githubuser.network.ViewState

fun ViewStateBinding.handleViewState(state: ViewState, requestType: NetworkRequestType) {
    handleLoadingType(requestType)
    when (state) {
        ViewState.LOADING -> {
            containerViewState.visibility = View.VISIBLE
            containerError.visibility = View.GONE
            containerLoading.visibility = View.VISIBLE
        }
        ViewState.SUCCESS -> {
            containerViewState.visibility = View.GONE
            containerError.visibility = View.GONE
            containerLoading.visibility = View.GONE
        }
        ViewState.ERROR -> {
            containerViewState.visibility = View.VISIBLE
            containerError.visibility = View.VISIBLE
            containerLoading.visibility = View.GONE
        }
    }
}

fun ViewStateBinding.setErrorMessage(message: String) {
    tvErrorMessage.text = message
}

fun ViewStateBinding.setOnRetakeClicked(onClick: () -> Unit) {
    btnRetake.setOnClickListener {
        onClick()
    }
}

private fun ViewStateBinding.handleLoadingType(requestType: NetworkRequestType) {
    when (requestType) {
        NetworkRequestType.USER_DETAIL -> {
            layoutShimmerUsers.containerMain.visibility = View.GONE
            layoutShimmerUserDetail.containerMain.visibility = View.VISIBLE
        }
        NetworkRequestType.LIST_USERS -> {
            layoutShimmerUsers.containerMain.visibility = View.VISIBLE
            layoutShimmerUserDetail.containerMain.visibility = View.GONE
        }
    }
}

package id.bachtiar.harits.githubuser.widget

import android.view.View
import id.bachtiar.harits.githubuser.databinding.ViewStateBinding
import id.bachtiar.harits.githubuser.network.ViewState

fun ViewStateBinding.handleViewState(state: ViewState) {
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

package id.bachtiar.harits.githubuser

import id.bachtiar.harits.githubuser.model.User

interface OnItemClickCallback {
    fun onItemClicked(data: User)
}
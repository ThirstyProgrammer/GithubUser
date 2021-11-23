package id.bachtiar.harits.githubuser

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.bachtiar.harits.githubuser.databinding.ItemUserBinding
import id.bachtiar.harits.githubuser.model.User

class UserViewHolder constructor(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        user: User,
        addOrRemoveFavourite: ((user: User) -> Unit)? = null,
        isFromFavouritePage: Boolean
    ) {
        binding.apply {
            val context = root.context
            Glide.with(context)
                .load(user.avatar)
                .into(imgItemPhoto)
            tvUsername.text = user.username
            tvGithubUrl.text = user.githubUrl
            setupFavourite(user, addOrRemoveFavourite, isFromFavouritePage)
        }
    }

    private fun setupFavourite(
        user: User,
        addOrRemoveFavourite: ((user: User) -> Unit)? = null,
        isFromFavouritePage: Boolean
    ) {
        binding.apply {
            if (addOrRemoveFavourite == null) {
                btnFavourite.visibility = View.GONE
            } else {
                btnFavourite.visibility = View.VISIBLE
                val drawable =
                    when {
                        isFromFavouritePage -> R.drawable.ic_delete
                        user.isFavourite -> R.drawable.ic_favourite_filled
                        else -> R.drawable.ic_favourite_outlined
                    }
                btnFavourite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        drawable
                    )
                )
                btnFavourite.setOnClickListener {
                    addOrRemoveFavourite(user)
                }
            }
        }
    }
}
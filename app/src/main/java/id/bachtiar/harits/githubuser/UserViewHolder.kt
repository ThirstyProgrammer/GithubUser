package id.bachtiar.harits.githubuser

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.bachtiar.harits.githubuser.databinding.ItemUserBinding
import id.bachtiar.harits.githubuser.model.User

class UserViewHolder constructor(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.apply {
            val context = root.context
            Glide.with(context)
                .load(user.avatar)
                .into(imgItemPhoto)
            tvUsername.text = user.username
            tvGithubUrl.text = user.githubUrl
        }
    }
}
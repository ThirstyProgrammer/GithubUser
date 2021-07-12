package id.bachtiar.harits.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.bachtiar.harits.githubuser.databinding.ItemUserBinding
import id.bachtiar.harits.githubuser.model.User

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var listener: OnItemClickCallback
    private val list = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding: ItemUserBinding =
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(data: List<User>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun addItem(item: User) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
    }

    fun setOnItemClickCallback(onItemClicCallback: OnItemClickCallback) {
        this.listener = onItemClicCallback
    }

    inner class UserViewHolder constructor(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                val context = root.context
                Glide.with(context)
                    .load(user.avatar_url)
                    .into(imgItemPhoto)
                tvUsername.text = user.username
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
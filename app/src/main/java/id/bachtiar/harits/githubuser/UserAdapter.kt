package id.bachtiar.harits.githubuser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.bachtiar.harits.githubuser.databinding.ItemUserBinding
import id.bachtiar.harits.githubuser.databinding.ItemUserShimmerBinding
import id.bachtiar.harits.githubuser.model.User

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
    }

    private lateinit var listener: OnItemClickCallback
    private val items = ArrayList<User>()
    private var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemUserBinding =
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return when (viewType) {
            TYPE_ITEM -> {
                UserViewHolder(binding)
            }
            else -> LoadMoreViewHolder(
                ItemUserShimmerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                holder.bind(items[position])
                if (::listener.isInitialized) {
                    holder.itemView.setOnClickListener {
                        listener.onItemClicked(items[position])
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int = items.size
    override fun getItemViewType(position: Int): Int = items[position].typeItem

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.listener = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<User>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<User>) {
        if (items.isNullOrEmpty()) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            val currentPosition = items.size
            removeFooter()
            items.addAll(data)
            notifyItemInserted(currentPosition)
        }
        if (data.isNotEmpty()) addFooter()
    }

    private fun addFooter() {
        isLoading = true
        items.add(User(typeItem = TYPE_LOADING))
        notifyItemInserted(items.size)
    }

    private fun removeFooter() {
        isLoading = false
        if (!items.isNullOrEmpty()) {
            items.removeAt(items.size - 1)
        }
        notifyItemRemoved(items.size)
    }
}
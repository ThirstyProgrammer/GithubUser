package id.bachtiar.harits.githubuser.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import id.bachtiar.harits.githubuser.OnItemClickCallback
import id.bachtiar.harits.githubuser.R
import id.bachtiar.harits.githubuser.UserAdapter
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.ui.detail.DetailFragment
import id.bachtiar.harits.githubuser.util.Constant
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_list), OnItemClickCallback {

    private lateinit var userAdapter: UserAdapter
    private val mBinding: FragmentListBinding by viewBinding()
    private val mViewModel: FavouriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        mViewModel.getUsers().observe(viewLifecycleOwner, { users ->
            val newList = users.map { it.mapToModel() }
            userAdapter.setData(newList)
            emptyView(newList.isEmpty())
        })
    }

    override fun onItemClicked(data: User) {
        val mDetailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constant.Extras.USER_DATA, data)
        mDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mDetailFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun getSearchUsername(username: String = "") {
        mViewModel.username = username
        if (username != "") {
            mViewModel.getSearchUsers().observe(viewLifecycleOwner, { users ->
                val newList = users.map { it.mapToModel() }
                userAdapter.setData(newList)
                emptyView(newList.isEmpty())
            })
        } else {
            mViewModel.getUsers().observe(viewLifecycleOwner, { users ->
                val newList = users.map { it.mapToModel() }
                userAdapter.setData(newList)
                emptyView(newList.isEmpty())
            })
        }
    }

    private fun setupView() {
        userAdapter = UserAdapter(true) {
            mViewModel.deleteUserFromFavourite(it)
        }
        userAdapter.setOnItemClickCallback(this)
        mBinding.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration =
                DividerItemDecoration(rvUser.context, linearLayoutManager.orientation)

            rvUser.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = userAdapter
                addItemDecoration(dividerItemDecoration)
            }
        }
    }

    private fun emptyView(isEmpty: Boolean) {
        mBinding.apply {
            tvEmptyMessage.text = getString(R.string.empty_search_user, mViewModel.username)
            tvEmptyMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }
}
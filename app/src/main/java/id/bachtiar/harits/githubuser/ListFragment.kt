package id.bachtiar.harits.githubuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.ui.detail.DetailFragment
import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.widget.handleViewState
import id.bachtiar.harits.githubuser.widget.setErrorMessage
import id.bachtiar.harits.githubuser.widget.setOnRetakeClicked
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), OnItemClickCallback {

    private lateinit var userAdapter: UserAdapter
    private val mBinding: FragmentListBinding by viewBinding()
    private val mViewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        mBinding.viewState.setOnRetakeClicked {
            mViewModel.getUsers()
        }
        handleViewModelObserver()
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

    @ExperimentalSerializationApi
    fun getSearchUsername(username: String = "") {
        mViewModel.username = username
        mViewModel.getUsers()
    }

    private fun handleViewModelObserver() {
        mViewModel.favouriteUsers.observe(viewLifecycleOwner, {
            mViewModel.getUsers()
        })
        mViewModel.users.observe(viewLifecycleOwner, {
            userAdapter.setData(mViewModel.generateList())
            emptyView(it.isEmpty())
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView() {
        userAdapter = UserAdapter {
            val message = if (it.isFavourite) {
                mViewModel.deleteUserFromFavourite(it)
                getString(R.string.remove_from_favourite, it.username?.uppercase())
            } else {
                mViewModel.addUserToFavourite(it)
                getString(R.string.add_to_favourite, it.username?.uppercase())
            }
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            )
                .show()
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
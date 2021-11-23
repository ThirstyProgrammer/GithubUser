package id.bachtiar.harits.githubuser.ui.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import id.bachtiar.harits.githubuser.R
import id.bachtiar.harits.githubuser.UserAdapter
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.util.PaginationScrollListener
import id.bachtiar.harits.githubuser.util.defaultEmpty
import id.bachtiar.harits.githubuser.widget.handleViewState
import id.bachtiar.harits.githubuser.widget.setErrorMessage
import id.bachtiar.harits.githubuser.widget.setOnRetakeClicked
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@AndroidEntryPoint
class FollowingFragment : Fragment(R.layout.fragment_list) {

    companion object {

        fun newInstance(url: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(Constant.Extras.URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var userAdapter: UserAdapter
    private val mBinding: FragmentListBinding by viewBinding()
    private val mViewModel: FollowingViewModel by viewModels()
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireArguments().containsKey(Constant.Extras.URL)) {
            mViewModel.url = requireArguments().getString(Constant.Extras.URL).defaultEmpty()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        mBinding.viewState.setOnRetakeClicked {
            mViewModel.getFollowing()
        }
        handleViewModelObserver()
        mViewModel.getFollowing()
    }

    private fun handleViewModelObserver() {
        mViewModel.following.observe(viewLifecycleOwner, {
            isLoading = false
            mViewModel.updatedFollowing.addAll(it)
            userAdapter.updateData(mViewModel.updatedFollowing)
            emptyView(mViewModel.updatedFollowing.isEmpty())
            if (it.isNullOrEmpty()) {
                mViewModel.isLastPage = true
            }
        })

        mViewModel.viewState.observe(viewLifecycleOwner, {
            mBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mBinding.viewState.setErrorMessage(it)
        })
    }

    @ExperimentalSerializationApi
    private fun setupView() {
        userAdapter = UserAdapter()
        mBinding.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration =
                DividerItemDecoration(rvUser.context, linearLayoutManager.orientation)

            rvUser.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = userAdapter
                addItemDecoration(dividerItemDecoration)
                addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                    override fun isLastPage(): Boolean = mViewModel.isLastPage

                    override fun isLoading(): Boolean = isLoading

                    override fun loadMoreItems() {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == userAdapter.itemCount.minus(
                                1
                            )
                        ) {
                            loadMore()
                        }
                    }

                })
            }

            tvEmptyMessage.text = getString(R.string.empty_following)
        }
    }

    private fun emptyView(isEmpty: Boolean) {
        mBinding.apply {
            tvEmptyMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    @ExperimentalSerializationApi
    private fun loadMore() {
        isLoading = true
        mViewModel.getFollowing(true)
    }
}
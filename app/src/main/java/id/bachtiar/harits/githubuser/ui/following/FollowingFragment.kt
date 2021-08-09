package id.bachtiar.harits.githubuser.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.bachtiar.harits.githubuser.UserAdapter
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.network.ViewState
import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.util.PaginationScrollListener
import id.bachtiar.harits.githubuser.util.defaultEmpty
import kotlinx.serialization.ExperimentalSerializationApi

class FollowingFragment : Fragment() {

    companion object {

        fun newInstance(url: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(Constant.Extras.URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mBinding: FragmentListBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mViewModel: FollowingViewModel
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        if (requireArguments().containsKey(Constant.Extras.URL)) {
            mViewModel.url = requireArguments().getString(Constant.Extras.URL).defaultEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = FragmentListBinding.inflate(inflater)
        return mBinding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
        setupViewState()
        handleViewModelObserver()
        mViewModel.getFollowing()
    }

    private fun handleViewModelObserver() {
        mViewModel.following.observe(viewLifecycleOwner, {
            isLoading = false
            userAdapter.updateData(it)
            if (it.isNullOrEmpty()) {
                mViewModel.isLastPage = true
            }
        })

        mViewModel.viewState.observe(viewLifecycleOwner, {
            handleViewState(it)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mBinding.tvErrorMessage.text = it.second
        })
    }

    private fun handleViewState(state: ViewState) {
        when (state) {
            ViewState.LOADING -> {
                mBinding.apply {
                    viewState.visibility = View.VISIBLE
                    containerError.visibility = View.GONE
                    containerLoading.visibility = View.VISIBLE
                }            }
            ViewState.SUCCESS -> {
                mBinding.apply {
                    viewState.visibility = View.GONE
                    containerError.visibility = View.GONE
                    containerLoading.visibility = View.GONE
                }            }
            ViewState.ERROR -> {
                mBinding.apply {
                    viewState.visibility = View.VISIBLE
                    containerError.visibility = View.VISIBLE
                    containerLoading.visibility = View.GONE
                }
            }
        }
    }

    @ExperimentalSerializationApi
    private fun setupViewState() {
        mBinding.apply {
            btnRetake.setOnClickListener {
                mViewModel.getFollowing()
            }
        }
    }

    @ExperimentalSerializationApi
    private fun showRecyclerList() {
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
        }
    }

    @ExperimentalSerializationApi
    private fun loadMore() {
        isLoading = true
        mViewModel.getFollowing(true)
    }
}
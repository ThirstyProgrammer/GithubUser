package id.bachtiar.harits.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.ui.detail.DetailFragment
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.network.ViewState
import id.bachtiar.harits.githubuser.util.Constant
import kotlinx.serialization.ExperimentalSerializationApi

class ListFragment : Fragment(), OnItemClickCallback {

    private lateinit var mBinding: FragmentListBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
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
        mViewModel.getUsers()
    }

    private fun handleViewModelObserver() {
        mViewModel.users.observe(viewLifecycleOwner, {
            userAdapter.setData(it)
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
                mViewModel.getUsers()
            }
        }
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

    private fun showRecyclerList() {
        userAdapter = UserAdapter()
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
}
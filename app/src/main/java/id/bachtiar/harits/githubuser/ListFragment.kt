package id.bachtiar.harits.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.ui.detail.DetailFragment
import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.widget.handleViewState
import id.bachtiar.harits.githubuser.widget.setErrorMessage
import id.bachtiar.harits.githubuser.widget.setOnRetakeClicked
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
        mBinding.viewState.setOnRetakeClicked {
            mViewModel.getUsers()
        }
        handleViewModelObserver()
        mViewModel.getUsers()
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
        mViewModel.users.observe(viewLifecycleOwner, {
            userAdapter.setData(it)
        })

        mViewModel.viewState.observe(viewLifecycleOwner, {
            mBinding.viewState.handleViewState(it)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mBinding.viewState.setErrorMessage(it.second)
        })
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
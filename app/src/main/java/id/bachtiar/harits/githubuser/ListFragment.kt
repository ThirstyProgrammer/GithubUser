package id.bachtiar.harits.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.bachtiar.harits.githubuser.databinding.FragmentListBinding
import id.bachtiar.harits.githubuser.detail.DetailFragment
import id.bachtiar.harits.githubuser.model.User

class ListFragment : Fragment(), UserAdapter.OnItemClickCallback {

    private lateinit var binding: FragmentListBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var mViewModel: MainViewModel
    private var list: ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListBinding.inflate(inflater)

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
        mViewModel.getUsers()

        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.users.observe(viewLifecycleOwner, Observer {
            userAdapter.setData(it)
        })

        mViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onItemClicked(data: User) {
        val mDetailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constant.Extras.USER_DATA, data)
        mDetailFragment.arguments = bundle
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.frame_container, mDetailFragment)
            addToBackStack(null)
            commit()
        }
    }

    // DATA FROM JSON FILE
//    private fun getDataUser(): ArrayList<User> {
//        val json: String = try {
//            val inputStream = requireContext().assets.open(getString(R.string.resource_name))
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//            inputStream.read(buffer)
//            inputStream.close()
//            String(buffer, Charsets.UTF_8)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            ""
//        }
//
//        val data: Result = Json.decodeFromString(json)
//        return data.users ?: arrayListOf()
//    }

    private fun showRecyclerList() {
        userAdapter = UserAdapter()
        userAdapter.setOnItemClickCallback(this)
        with(binding) {
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
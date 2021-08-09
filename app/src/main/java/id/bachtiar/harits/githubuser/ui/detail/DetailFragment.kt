package id.bachtiar.harits.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.bachtiar.harits.githubuser.*
import id.bachtiar.harits.githubuser.databinding.FragmentDetailBinding
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.ui.followers.FollowersFragment
import id.bachtiar.harits.githubuser.ui.following.FollowingFragment
import id.bachtiar.harits.githubuser.util.Constant
import id.bachtiar.harits.githubuser.util.defaultDash
import id.bachtiar.harits.githubuser.util.defaultEmpty
import id.bachtiar.harits.githubuser.util.removeQueryParams
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailBinding
    private lateinit var mViewModel: DetailViewModel
    private var user: User = User()
    private val titlesViewPager: ArrayList<String> = arrayListOf()
    private val fragmentsViewPager: ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        if (requireArguments().containsKey(Constant.Extras.USER_DATA)) {
            user = requireArguments().getParcelable(Constant.Extras.USER_DATA) ?: User()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = FragmentDetailBinding.inflate(inflater)
        return mBinding.root
    }


    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarTitle()
        mViewModel.getUserDetail(user.url.defaultEmpty())
        handleViewModelObserver()
    }

    private fun initViewPager(user: User) {
        titlesViewPager.add(getString(R.string.tab_followers))
        fragmentsViewPager.add(FollowersFragment.newInstance(user.followersUrl.defaultEmpty()))
        titlesViewPager.add(getString(R.string.tab_following))
        fragmentsViewPager.add(FollowingFragment.newInstance(user.followingUrl.removeQueryParams()))
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragmentsViewPager)
        mBinding.apply {
            viewPager.apply {
                if (adapter == null) {
                    adapter = viewPagerAdapter
                    offscreenPageLimit = fragmentsViewPager.size

                }
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = titlesViewPager[position]
            }.attach()
        }
    }

    private fun handleViewModelObserver() {
        mViewModel.user.observe(viewLifecycleOwner, {
            setupView(it)
            initViewPager(it)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it.second, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupToolbarTitle() {
        (requireActivity() as MainActivity).supportActionBar?.title = user.username
    }

    private fun setupView(user: User) {
        mBinding.apply {
            Glide.with(requireContext())
                .load(user.avatar)
                .into(imgItemPhoto)
            tvName.text = user.username
            tvFollower.text = getString(R.string.follower, user.followers.toString())
            tvFollowing.text = getString(R.string.following, user.following.toString())
            tvRepository.text = getString(R.string.repository, user.repos.toString())
            tvLocation.text = user.location.defaultDash()
            tvCompany.text = user.company.defaultDash()
            btnShare.setOnClickListener {
                share(user)
            }
        }
    }

    private fun share(user: User) {
        val text =
            getString(
                R.string.share_template,
                user.username.defaultEmpty().toUpperCase(Locale.ROOT),
                user.name,
                getString(
                    R.string.follower,
                    user.followers.toString()
                ),
                getString(R.string.following, user.following.toString()),
                getString(R.string.repository, user.repos.toString()),
                user.location,
                user.company
            )
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share))
        startActivity(shareIntent)
    }
}
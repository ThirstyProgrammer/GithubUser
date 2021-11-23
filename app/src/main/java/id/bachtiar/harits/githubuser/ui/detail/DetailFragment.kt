package id.bachtiar.harits.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.bachtiar.harits.githubuser.MainActivity
import id.bachtiar.harits.githubuser.R
import id.bachtiar.harits.githubuser.ViewPagerAdapter
import id.bachtiar.harits.githubuser.databinding.FragmentDetailBinding
import id.bachtiar.harits.githubuser.model.User
import id.bachtiar.harits.githubuser.ui.favourite.FavouriteActivity
import id.bachtiar.harits.githubuser.ui.followers.FollowersFragment
import id.bachtiar.harits.githubuser.ui.following.FollowingFragment
import id.bachtiar.harits.githubuser.util.*
import id.bachtiar.harits.githubuser.widget.handleViewState
import id.bachtiar.harits.githubuser.widget.setErrorMessage
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@ExperimentalSerializationApi
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val mBinding: FragmentDetailBinding by viewBinding()
    private val mViewModel: DetailViewModel by viewModels()
    private var user: User = User()
    private val titlesViewPager: ArrayList<String> = arrayListOf()
    private val fragmentsViewPager: ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireArguments().containsKey(Constant.Extras.USER_DATA)) {
            user = requireArguments().getParcelable(Constant.Extras.USER_DATA) ?: User()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarTitle()
        mViewModel.getUserDetail(user.url.defaultEmpty())
        handleViewModelObserver()
    }

    private fun initViewPager(user: User) {
        if (titlesViewPager.isEmpty()) {
            titlesViewPager.add(getString(R.string.tab_followers))
            fragmentsViewPager.add(FollowersFragment.newInstance(user.followersUrl.defaultEmpty()))
            titlesViewPager.add(getString(R.string.tab_following))
            fragmentsViewPager.add(FollowingFragment.newInstance(user.followingUrl.removeQueryParams()))
        }
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

        mViewModel.viewState.observe(viewLifecycleOwner, {
            mBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupToolbarTitle() {
        mBinding.appBar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> {
                        mBinding.containerToolbar.visibility = View.GONE
                    }
                    State.COLLAPSED -> {
                        mBinding.containerToolbar.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }
        })
        mBinding.tvTitleToolbar.text = user.username
        mBinding.btnBackToolbar.setOnClickListener {
            when (requireActivity()) {
                is MainActivity -> {
                    (requireActivity() as MainActivity).popFragment()
                }
                is FavouriteActivity -> {
                    (requireActivity() as FavouriteActivity).popFragment()
                }
            }
        }
        mBinding.btnBack.setOnClickListener {
            when (requireActivity()) {
                is MainActivity -> {
                    (requireActivity() as MainActivity).popFragment()
                }
                is FavouriteActivity -> {
                    (requireActivity() as FavouriteActivity).popFragment()
                }
            }
        }
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
                user.username.defaultEmpty().uppercase(),
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
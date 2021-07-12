package id.bachtiar.harits.githubuser.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.bachtiar.harits.githubuser.Constant
import id.bachtiar.harits.githubuser.MainActivity
import id.bachtiar.harits.githubuser.R
import id.bachtiar.harits.githubuser.databinding.FragmentDetailBinding
import id.bachtiar.harits.githubuser.model.User
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireArguments().containsKey(Constant.Extras.USER_DATA)) {
            user = requireArguments().getParcelable(Constant.Extras.USER_DATA) ?: User()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbarTitle()
        setupView()
    }

    private fun setupToolbarTitle() {
        (requireActivity() as MainActivity).supportActionBar?.title = user.username
    }

    private fun setupView() {
        binding.apply {
//            val resource =
//                resources.getIdentifier(user.avatar, "drawable", requireContext().packageName)
//            Glide.with(requireContext())
//                .load(resource)
//                .into(imgItemPhoto)
//            tvName.text = user.name
//            tvFollower.text = getString(R.string.follower, user.follower.toString())
//            tvFollowing.text = getString(R.string.following, user.following.toString())
//            tvRepository.text = getString(R.string.repository, user.repository.toString())
//            tvLocation.text = user.location
//            tvCompany.text = user.company
            btnShare.setOnClickListener {
//                share()
            }
        }
    }

//    private fun share() {
//        val text =
//            getString(
//                R.string.share_template,
//                user.username.toUpperCase(Locale.ROOT),
//                user.name,
//                getString(
//                    R.string.follower,
//                    user.follower.toString()
//                ),
//                getString(R.string.following, user.following.toString()),
//                getString(R.string.repository, user.repository.toString()),
//                user.location,
//                user.company
//            )
//        val sendIntent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
//            putExtra(Intent.EXTRA_TEXT, text)
//            type = "text/plain"
//        }
//        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share))
//        startActivity(shareIntent)
//    }
}
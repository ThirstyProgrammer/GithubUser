package id.bachtiar.harits.githubuser.ui.favourite

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.bachtiar.harits.githubuser.R
import id.bachtiar.harits.githubuser.databinding.ActivityFavouriteBinding
import id.bachtiar.harits.githubuser.util.defaultEmpty
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

@ExperimentalSerializationApi
@AndroidEntryPoint
class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private val stack: Stack<Fragment> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        initFavouriteFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favourite_menu, menu)
        setupSearchView(menu)
        return true
    }

    override fun onBackPressed() {
        if (!stack.isEmpty()) {
            stack.pop()
            initFavouriteFragment()
        } else super.onBackPressed()
    }

    fun popFragment() {
        if (!stack.isEmpty()) {
            stack.pop()
            initFavouriteFragment()
        } else super.onBackPressed()
    }

    private fun initFavouriteFragment() {
        val mFragmentManager = supportFragmentManager
        val fragment = mFragmentManager.findFragmentByTag(FavouriteFragment::class.java.simpleName)

        if (fragment !is FavouriteFragment) {
            mFragmentManager.beginTransaction()
                .add(
                    R.id.frame_container,
                    FavouriteFragment(),
                    FavouriteFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setActionBarState()
        supportFragmentManager.addOnBackStackChangedListener {
            setActionBarState()
        }
    }

    private fun setActionBarState() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            binding.toolbar.visibility = View.GONE
        } else {
            supportActionBar?.title = getString(R.string.favourite)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    @ExperimentalSerializationApi
    private fun setupSearchView(menu: Menu?) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val searchEditText = searchView.findViewById<EditText>(R.id.search_src_text)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.background = ContextCompat.getDrawable(this, R.drawable.bg_search)
        searchView.queryHint = getString(R.string.search_hint)
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.color_primary))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.color_primary_light))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                when (val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)) {
                    is FavouriteFragment -> fragment.getSearchUsername(query.defaultEmpty())
                    else -> Unit
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                when (val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)) {
                    is FavouriteFragment -> fragment.getSearchUsername(query.defaultEmpty())
                    else -> Unit
                }
                return false
            }

        })
    }
}
package id.bachtiar.harits.githubuser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import id.bachtiar.harits.githubuser.databinding.ActivityMainBinding
import id.bachtiar.harits.githubuser.util.defaultEmpty
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val stack: Stack<Fragment> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        initListFragment()
    }

    @ExperimentalSerializationApi
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        setupSearchView(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> Toast.makeText(this, "HEEELODFSODF", Toast.LENGTH_LONG).show()
            else -> Unit
        }
        return true
    }

    override fun onBackPressed() {
        if (!stack.isEmpty()) {
            stack.pop()
            initListFragment()
        } else super.onBackPressed()
    }

    fun popFragment() {
        if (!stack.isEmpty()) {
            stack.pop()
            initListFragment()
        } else super.onBackPressed()
    }

    private fun initListFragment() {
        val mFragmentManager = supportFragmentManager
        val fragment = mFragmentManager.findFragmentByTag(ListFragment::class.java.simpleName)

        if (fragment !is ListFragment) {
            mFragmentManager.beginTransaction()
                .add(R.id.frame_container, ListFragment(), ListFragment::class.java.simpleName)
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
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                binding.toolbar.visibility = View.GONE
            }
        } else {
            if (supportActionBar != null) {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                binding.toolbar.visibility = View.VISIBLE
            }
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
                val listFragment =
                    supportFragmentManager.findFragmentById(R.id.frame_container) as ListFragment
                listFragment.getSearchUsername(query.defaultEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val listFragment =
                    supportFragmentManager.findFragmentById(R.id.frame_container) as ListFragment
                if (newText.isNullOrEmpty()) listFragment.getSearchUsername()
                return false
            }

        })
    }
}
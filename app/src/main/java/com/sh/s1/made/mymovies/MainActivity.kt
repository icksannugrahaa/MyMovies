package com.sh.s1.made.mymovies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.sh.s1.made.mymovies.databinding.ActivityMainBinding
import com.sh.s1.made.mymovies.home.HomeFragment
import com.sh.s1.made.mymovies.search.SearchFragment

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeFragment = HomeFragment()
        setCurrentFragment(homeFragment)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.bnvMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_nav_popular -> setCurrentFragment(homeFragment)
                R.id.btn_nav_favorite -> {
                    val uri = Uri.parse("mymovies://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val homeFragment = HomeFragment()
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val searchFragment = SearchFragment.newInstance(query)
                setCurrentFragment(searchFragment)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == "") {
                    setCurrentFragment(homeFragment)
                } else {
                    val searchFragment = SearchFragment.newInstance(newText)
                    setCurrentFragment(searchFragment)
                }
                return false
            }
        })

        return true
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }
}
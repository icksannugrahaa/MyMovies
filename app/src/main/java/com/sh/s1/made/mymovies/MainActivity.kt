package com.sh.s1.made.mymovies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.sh.s1.made.mymovies.core.data.Resource
import com.sh.s1.made.mymovies.core.ui.SearchAdapter
import com.sh.s1.made.mymovies.core.ui.ViewModelFactory
import com.sh.s1.made.mymovies.databinding.ActivityMainBinding
import com.sh.s1.made.mymovies.detail.DetailMovieActivity
import com.sh.s1.made.mymovies.domain.model.Movie
import com.sh.s1.made.mymovies.favorite.FavoriteFragment
import com.sh.s1.made.mymovies.home.HomeFragment
import com.sh.s1.made.mymovies.search.SearchFragment
import com.sh.s1.made.mymovies.search.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        setCurrentFragment(homeFragment)

        setSupportActionBar(binding.appBarMain.toolbar)

//        val factory = ViewModelFactory.getInstance(this)
//        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.bnvMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_nav_popular -> setCurrentFragment(homeFragment)
                R.id.btn_nav_favorite -> setCurrentFragment(favoriteFragment)
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
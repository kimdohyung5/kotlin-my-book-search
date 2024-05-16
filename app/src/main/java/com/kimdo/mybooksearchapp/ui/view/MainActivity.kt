package com.kimdo.mybooksearchapp.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kimdo.mybooksearchapp.R
import com.kimdo.mybooksearchapp.data.api.BookSearchApi
import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl
import com.kimdo.mybooksearchapp.databinding.ActivityMainBinding
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModelProviderFactory
import com.kimdo.mybooksearchapp.util.Constants.DATASTORE_NAME

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root )


//        setupBottomNavigationView()
//        // 재생성되면 그쪽으로 가나?
//        if( savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
//        }

        setupJetpackNavigation()

        val database = BookSearchDatabase.getInstance(this)
        val repository = BookSearchRepositoryImpl(database, dataStore)
        val factory = BookSearchViewModelProviderFactory(repository, this)
        bookSearchViewModel = ViewModelProvider(this, factory )[BookSearchViewModel::class.java]
    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            //navController.graph
            setOf(R.id.fragment_search,
            R.id.fragment_favorite,
            R.id.fragment_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp( appBarConfiguration ) ||super.onSupportNavigateUp()
    }

//    private fun setupBottomNavigationView() {
//        binding.bottomNavigationView.setOnItemSelectedListener {item ->
//            when( item.itemId ) {
//                R.id.fragment_search -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SearchFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FavoriteFragment())
//                        .commit()
//                    true
//
//                }
//                R.id.fragment_settings -> {
//                    Log.d("xxxx", "xxx")
//                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SettingsFragment())
//                        .commit()
//                    true
//
//                }
//                else -> false
//
//            }
//
//        }
//    }
}
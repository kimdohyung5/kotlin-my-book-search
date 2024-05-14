package com.kimdo.mybooksearchapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kimdo.mybooksearchapp.R
import com.kimdo.mybooksearchapp.data.api.BookSearchApi
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl
import com.kimdo.mybooksearchapp.databinding.ActivityMainBinding
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var bookSearchViewModel: BookSearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root )

        val repository = BookSearchRepositoryImpl()
        val factory = BookSearchViewModelProviderFactory(repository, this)
        bookSearchViewModel = ViewModelProvider(this, factory )[BookSearchViewModel::class.java]


        setupBottomNavigationView()
        // 재생성되면 그쪽으로 가나?
        if( savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {item ->
            when( item.itemId ) {
                R.id.fragment_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SearchFragment())
                        .commit()
                    true
                }
                R.id.fragment_favorite -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FavoriteFragment())
                        .commit()
                    true

                }
                R.id.fragment_settings -> {
                    Log.d("xxxx", "xxx")
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SettingsFragment())
                        .commit()
                    true

                }
                else -> false

            }

        }
    }
}
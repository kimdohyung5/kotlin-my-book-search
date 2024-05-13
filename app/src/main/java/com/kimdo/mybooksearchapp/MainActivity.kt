package com.kimdo.mybooksearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kimdo.mybooksearchapp.databinding.ActivityMainBinding
import com.kimdo.mybooksearchapp.ui.view.FavoriteFragment
import com.kimdo.mybooksearchapp.ui.view.SearchFragment
import com.kimdo.mybooksearchapp.ui.view.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root )


        setupBottomNavigationView()
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
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SettingsFragment())
                        .commit()
                    true

                }
                else -> false

            }

        }
    }
}
package com.nbstocks.nbstocks.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nbstocks.nbstocks.R
import com.nbstocks.nbstocks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.companyListingsFragment, R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfig)
        binding.bottomNavigationView.setupWithNavController(navController)
        hideToolBar()
    }

    fun showToolBar() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.fabStocks.visibility = View.VISIBLE
    }

    fun hideToolBar() {
        binding.bottomNavigationView.visibility = View.GONE
        binding.bottomAppBar.visibility = View.GONE
        binding.fabStocks.visibility = View.GONE
    }
}
package com.nbstocks.nbstocks.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.nbstocks.nbstocks.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



        navView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)


        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.companyListingsFragment,
                R.id.profileFragment
            )
        )


        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.logInFragment -> hideToolBar()
                R.id.signUpFragment -> hideToolBar()
                R.id.passwordRecoveryFragment -> hideToolBar()
                R.id.stocksDetailsFragment -> hideToolBar()
                R.id.watchlistFragment -> hideToolBar()
                R.id.userStockListingFragment -> hideToolBar()
                else -> showToolBar()
            }
        }

    }


    private fun showToolBar() {
        navView.isVisible = true
    }

    private fun hideToolBar() {
        navView.isVisible = false
    }


}
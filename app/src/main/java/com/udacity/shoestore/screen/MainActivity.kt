package com.udacity.shoestore.screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    private val sharedViewModel: SharedViewModel by viewModels()

    var currentDestination: NavDestination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedViewModel.authData.observe(
            this,
            Observer { authData ->
                if (authData.isLoggedIn && currentDestination?.id in getNonAuthorizedDestinations()) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.welcomeFragment)
                } else if (!authData.isLoggedIn && !(currentDestination?.id in getNonAuthorizedDestinations())) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
                }
            }
        )

        sharedViewModel.errorTextResource.observe(
            this,
            Observer {
                it?.let {
                    Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_LONG).show()
                    sharedViewModel.showErrorComplete()
                }
            }
        )

        setupNavigation()
    }

    /**
     * Called when the hamburger menu or back button are pressed on the Toolbar
     *
     * Delegate this to Navigation.
     */
    override fun onSupportNavigateUp() = navigateUp(
        findNavController(R.id.nav_host_fragment),
        appBarConfiguration
    ) || super.onSupportNavigateUp()

    /**
     * Setup Navigation for this Activity
     */
    private fun setupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration.Builder(getRootDestinations())
            .setOpenableLayout(binding.drawerLayout)
            .setFallbackOnNavigateUpListener { onSupportNavigateUp() }
            .build()

        // then setup the action bar, tell it about the DrawerLayout
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // finally setup the left drawer (called a NavigationView)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            currentDestination = destination
            binding.toolbar.isVisible = shouldShowToolbarForDestination(destination.id)
        }
    }

    private fun getRootDestinations(): Set<Int> {
        return setOf(
            R.id.shoeListFragment,
            R.id.instructionFragment
        )
    }

    private fun shouldShowToolbarForDestination(destinationId: Int): Boolean {
        return destinationId !in setOf(
            R.id.loginFragment,
            R.id.welcomeFragment
        )
    }

    private fun getNonAuthorizedDestinations(): Set<Int> {
        return setOf(
            R.id.loginFragment
        )
    }
}

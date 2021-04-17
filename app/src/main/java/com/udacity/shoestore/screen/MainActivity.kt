package com.udacity.shoestore.screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var sharedViewModel: SharedViewModel

    var currentDestination: NavDestination? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.authData.observe(
            this,
            Observer { authData ->
                if (authData.isLoggedIn && currentDestination?.id != R.id.shoeListFragment) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.shoeListFragment)
                } else if (currentDestination?.id != R.id.loginFragment) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sharedViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
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
}

package com.tuit.tuit.ui.student

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tuit.tuit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

import android.webkit.WebView
import com.tuit.tuit.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun hideBottomAppBar() {
        binding.run {
            bottomBar.performHide()
            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
            // the fab and bottom app bar until after it's exit animation finishes.
            bottomBar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator?) {
                    if (isCanceled) return

                    // Hide the BottomAppBar to avoid it showing above the keyboard
                    // when composing a new email.
                    bottomBar.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    isCanceled = true
                }
            })
        }
    }

    private fun showBottomNav() {
        binding.apply {
            bottomBar.visibility = View.VISIBLE
            binding.bottomBar.performShow()
        }

    }


    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.navView.itemIconTintList = null

        binding.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.fragmentTest -> {
                        hideBottomAppBar()
                    }
                    R.id.fragmentVariants -> {
                        hideBottomAppBar()
                    }

                    R.id.navigation_home -> {
                        showBottomNav()
                    }

                    R.id.navigation_dashboard -> {
                        showBottomNav()
                    }
                    R.id.navigation_notifications -> {
                        showBottomNav()
                    }
                    R.id.subjectsFragment -> {
                        hideBottomAppBar()
                    }


                    R.id.openFileFragment -> {
                        hideBottomAppBar()
                    }

                }
            }
        }
        navView.setupWithNavController(navController)
        binding.navView.setOnNavigationItemReselectedListener {}
    }
}
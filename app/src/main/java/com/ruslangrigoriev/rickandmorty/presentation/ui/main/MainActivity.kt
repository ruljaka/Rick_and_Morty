package com.ruslangrigoriev.rickandmorty.presentation.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.ActivityMainBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.networkTracker.NetworkStatus
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentNavigator {

    @Inject
    lateinit var viewModel: MainViewModel
    private var keep = true
    private val delay = 1000L
    private val binding by viewBinding(ActivityMainBinding::bind)
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        startSplashScreen()
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        observeConnection()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavigation()
    }

    private fun startSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keep }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            keep = false
            binding.bttmNav.selectedItemId = R.id.characters
        }, delay)
    }

    private fun setupBottomNavigation() {
        binding.bttmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    if (currentFragment !is CharactersFragment) navigate(CharactersFragment())
                    true
                }
                R.id.locations -> {
                    if (currentFragment !is LocationsFragment) navigate(LocationsFragment())
                    true
                }
                else -> {
                    if (currentFragment !is EpisodesFragment) navigate(EpisodesFragment())
                    true
                }
            }
        }
    }

    private fun observeConnection() {
        lifecycleScope.launchWhenResumed {
            viewModel.networkState.collect {
                when (it) {
                    NetworkStatus.Available -> {
                        showToast(this@MainActivity, "Network available")
                        viewModel.setNetworkStatus(true)
                    }
                    NetworkStatus.Unavailable -> {
                        showToast(this@MainActivity, "Network unavailable \n   Going offline")
                        viewModel.setNetworkStatus(false)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        currentFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment, "CURRENT_FRAGMENT")
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }

}
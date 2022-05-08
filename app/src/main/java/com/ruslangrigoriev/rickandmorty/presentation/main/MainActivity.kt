package com.ruslangrigoriev.rickandmorty.presentation.main

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
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFragment
import com.ruslangrigoriev.rickandmorty.presentation.network.NetworkStatus
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentNavigator {

    @Inject
    lateinit var viewModel: MainViewModel
    private var keep = true
    private val delay = 1000L
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        startSplashScreen(savedInstanceState)
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)
        observeConnection()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavigation()
    }

    private fun startSplashScreen(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keep }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            keep = false
            if (savedInstanceState == null) {
                binding.bttmNav.selectedItemId = R.id.characters
            }
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
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                when (it) {
                    NetworkStatus.Available -> {
                        "Network Available".showToast(this@MainActivity)
                        viewModel.setNetworkStatus(true)
                    }
                    NetworkStatus.Unavailable -> {
                        "Network Unavailable".showToast(this@MainActivity)
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

    override var currentFragment: Fragment? = null
        get() = supportFragmentManager.findFragmentByTag("CURRENT_FRAGMENT")
        set(value) {
            field = value
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
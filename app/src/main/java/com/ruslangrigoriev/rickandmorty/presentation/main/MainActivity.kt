package com.ruslangrigoriev.rickandmorty.presentation.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.common.showToast
import com.ruslangrigoriev.rickandmorty.databinding.ActivityMainBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFragment
import com.ruslangrigoriev.rickandmorty.presentation.network.NetworkStatus
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigator: FragmentNavigator

    @Inject
    lateinit var viewModel: MainViewModel
    private var keep = true
    private val delay = 1000L
    private val binding by viewBinding(ActivityMainBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        startSplashScreen(savedInstanceState)
        super.onCreate(savedInstanceState)

        this.appComponent.inject(this)
        checkConnection()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavigation()
        checkConnection()

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
                    navigator.navigate(this, CharactersFragment())
                    true
                }
                R.id.locations -> {
                    navigator.navigate(this, LocationsFragment())
                    true
                }
                else -> {
                    navigator.navigate(this, EpisodesFragment())
                    true
                }
            }
        }
    }

    private fun checkConnection() {
        lifecycleScope.launchWhenCreated() {
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

}
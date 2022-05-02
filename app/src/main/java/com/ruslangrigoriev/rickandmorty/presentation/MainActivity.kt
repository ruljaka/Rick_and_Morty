package com.ruslangrigoriev.rickandmorty.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.databinding.ActivityMainBinding
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject lateinit var navigator: FragmentNavigator
    private var keep = true
    private val delay = 1000L
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        this.appComponent.inject(this)

        splashScreen.setKeepOnScreenCondition { keep }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ keep = false }, delay)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavigation()
        if(savedInstanceState == null) {
            binding.bttmNav.selectedItemId = R.id.characters
        }
    }

    private fun setupBottomNavigation() {
        binding.bttmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    navigator.navigate(this,CharactersFragment())
                    true
                }
                R.id.locations -> {
                    navigator.navigate(this,LocationsFragment())
                    true
                }
                else -> {
                    navigator.navigate(this,EpisodesFragment())
                    true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
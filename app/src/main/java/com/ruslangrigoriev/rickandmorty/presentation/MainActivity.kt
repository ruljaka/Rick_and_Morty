package com.ruslangrigoriev.rickandmorty.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.ActivityMainBinding
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentNavigator {

    private var keep = true
    private val DELAY = 1250L

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { keep }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ keep = false }, DELAY)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        binding.bttmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    toCharacters()
                    true
                }
                R.id.locations -> {
                    toLocations()
                    true
                }
                else -> {
                    toEpisodes()
                    true
                }
            }
        }
        binding.bttmNav.selectedItemId = R.id.characters
    }

    override fun toCharacters() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, CharactersFragment())
            .commit()
    }

    override fun toCharacterDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, CharacterDetailsFragment())
            .addToBackStack("CharacterDetailsFragment")
            .commit()
    }

    override fun toLocations() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, LocationsFragment())
            .commit()
    }

    override fun toLocationDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, LocationDetailsFragment())
            .addToBackStack("LocationDetailsFragment")
            .commit()
    }

    override fun toEpisodes() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, EpisodesFragment())
            .commit()
    }

    override fun toEpisodeDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, EpisodeDetailsFragment())
            .addToBackStack("EpisodeDetailsFragment")
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
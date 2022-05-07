package com.ruslangrigoriev.rickandmorty.presentation.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ruslangrigoriev.rickandmorty.R

interface FragmentNavigator {
    var currentFragment: Fragment?
    fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean = false
    )
}
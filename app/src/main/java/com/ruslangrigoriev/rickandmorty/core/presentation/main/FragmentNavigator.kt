package com.ruslangrigoriev.rickandmorty.core.presentation.main

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean = false
    )
}
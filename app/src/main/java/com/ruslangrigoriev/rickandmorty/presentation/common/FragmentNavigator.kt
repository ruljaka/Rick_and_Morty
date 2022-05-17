package com.ruslangrigoriev.rickandmorty.presentation.common

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean = false
    )
}
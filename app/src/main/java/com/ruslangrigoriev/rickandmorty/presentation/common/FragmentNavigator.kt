package com.ruslangrigoriev.rickandmorty.presentation.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ruslangrigoriev.rickandmorty.R

class FragmentNavigator {
    fun navigate(
        activity: AppCompatActivity,
        fragment: Fragment,
        addToBackStack: Boolean = false
    ) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}
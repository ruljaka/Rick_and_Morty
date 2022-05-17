package com.ruslangrigoriev.rickandmorty.presentation.common

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.ruslangrigoriev.rickandmorty.App
import com.ruslangrigoriev.rickandmorty.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }

val Context.navigator: FragmentNavigator?
    get() = when (this) {
        is FragmentNavigator -> this
        else -> null
    }

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun List<String>.toListIds(): List<Int> {
    return this.map { it.getId()!! }
}

fun String.getId(): Int? = Uri.parse(this).lastPathSegment?.toInt()

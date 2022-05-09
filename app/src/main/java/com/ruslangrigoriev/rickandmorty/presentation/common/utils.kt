package com.ruslangrigoriev.rickandmorty.presentation.common

import android.content.Context
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

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun List<String>.toListIds(): List<Int> {
    return this.map { (it.replace("\"", "").substringAfterLast('/')).toInt() }
}

fun String.getId(): Int? {
    return if (this.isNotEmpty()) {
        this.replace("\"", "")
            .substringAfterLast('/').toInt()
    } else null
}
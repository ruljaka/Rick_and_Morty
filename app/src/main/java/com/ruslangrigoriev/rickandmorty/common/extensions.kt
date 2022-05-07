package com.ruslangrigoriev.rickandmorty.common

import android.content.Context
import android.widget.Toast
import com.ruslangrigoriev.rickandmorty.App
import com.ruslangrigoriev.rickandmorty.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun List<String>.toListIds(): List<Int> {
    return this.map { (it.replace("\"", "").substringAfterLast('/')).toInt() }
}

fun List<Int>.toRequestString(): String {
    return this.joinToString(prefix = "[", postfix = "]")
}

fun String.getId(): Int? {
    return if (this.isNotEmpty()) {
        this.replace("\"", "")
            .substringAfterLast('/').toInt()
    } else null
}

suspend inline fun <T> safeApiCall(
    crossinline call: suspend () -> T
): T {
    return try {
        call()
    } catch (e: Exception) {
        throw Throwable(e.localizedMessage)
    }
}



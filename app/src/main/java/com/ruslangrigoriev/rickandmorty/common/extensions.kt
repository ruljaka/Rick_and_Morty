package com.ruslangrigoriev.rickandmorty.common

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.ruslangrigoriev.rickandmorty.App
import com.ruslangrigoriev.rickandmorty.data.remote.ErrorResponse
import com.ruslangrigoriev.rickandmorty.di.AppComponent
import retrofit2.Response

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun <T> Response<T>.processApiCall(): T? {
    if (this.isSuccessful) {
        return this.body()
    } else {
        try {
            val responseError = Gson().fromJson(
                this.errorBody()?.string(),
                ErrorResponse::class.java
            )
            throw Throwable(responseError.statusMessage)
        } catch (e: Exception) {
            throw Throwable("Api call failed ${e.message ?: e.toString()}")
        }
    }
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



package com.ruslangrigoriev.rickandmorty.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <T> getRemoteOrCachedData(
    isNetworkAvailable: Boolean,
    crossinline networkCall: suspend () -> Response<T>,
    crossinline databaseQuery: suspend () -> T?,
    crossinline saveCallResult: suspend (T) -> Unit
): T? = withContext(Dispatchers.IO) {
    if (isNetworkAvailable) {
        return@withContext  networkCall().body()
            ?.apply { saveCallResult(this) }
    } else databaseQuery.invoke()
}

fun String.getKey(): Int? {
    return if (this.isNotEmpty()) {
        this.replace("\"", "")
            .substringAfter('=')
            .substringBefore('&')
            .toInt()
    } else null
}









package com.ruslangrigoriev.rickandmorty.data

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
        return@withContext networkCall().body()
            ?.apply { saveCallResult(this) }
    } else databaseQuery.invoke()
}

fun List<Int>.toRequestString(): String {
    return this.joinToString(prefix = "[", postfix = "]")
}

fun String?.getKey(): Int? = this?.let {
    replace("\"", "")
        .substringAfter('=')
        .substringBefore('&')
        .toInt()
}










package com.ruslangrigoriev.rickandmorty.presentation.common

sealed class RequestState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RequestState<T>(data = data)
    class Error<T>(message: String) : RequestState<T>(message = message)
    class Loading<T> : RequestState<T>()
}
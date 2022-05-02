package com.ruslangrigoriev.rickandmorty.data.remote

import com.ruslangrigoriev.rickandmorty.presentation.RequestState
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): RequestState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return RequestState.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): RequestState<T> =
        RequestState.Error("Api call failed $errorMessage")
}
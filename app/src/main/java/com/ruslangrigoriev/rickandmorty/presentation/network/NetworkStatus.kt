package com.ruslangrigoriev.rickandmorty.presentation.network

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

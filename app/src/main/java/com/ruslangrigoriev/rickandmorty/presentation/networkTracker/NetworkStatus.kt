package com.ruslangrigoriev.rickandmorty.presentation.networkTracker

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

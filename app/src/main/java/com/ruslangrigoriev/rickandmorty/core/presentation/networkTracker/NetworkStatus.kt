package com.ruslangrigoriev.rickandmorty.core.presentation.networkTracker

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

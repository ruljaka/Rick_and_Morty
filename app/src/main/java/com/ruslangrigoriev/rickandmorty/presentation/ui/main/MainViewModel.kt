package com.ruslangrigoriev.rickandmorty.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.SetNetworkAvailabilityUseCase
import com.ruslangrigoriev.rickandmorty.presentation.networkTracker.NetworkStatus
import com.ruslangrigoriev.rickandmorty.presentation.networkTracker.NetworkStatusTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainViewModel @Inject constructor(
    networkStatusTracker: NetworkStatusTracker,
    private val setNetworkAvailabilityUseCase: SetNetworkAvailabilityUseCase

) : ViewModel() {

    private val _networkState = networkStatusTracker.networkStatus.flowOn(Dispatchers.IO)
    val networkState: Flow<NetworkStatus>
        get() = _networkState

    fun setNetworkStatus(status: Boolean) {
        setNetworkAvailabilityUseCase(status)
    }
}
package com.ruslangrigoriev.rickandmorty.presentation.main

import androidx.lifecycle.ViewModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.SetNetworkAvailabilityUseCase
import com.ruslangrigoriev.rickandmorty.presentation.network.NetworkStatus
import com.ruslangrigoriev.rickandmorty.presentation.network.NetworkStatusTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker,
    private val setNetworkAvailabilityUseCase: SetNetworkAvailabilityUseCase

) : ViewModel() {

    private val _state = networkStatusTracker.networkStatus.flowOn(Dispatchers.IO)
    val state: Flow<NetworkStatus>
        get() = _state

    fun setNetworkStatus(status: Boolean) {
        setNetworkAvailabilityUseCase(status)
    }
}
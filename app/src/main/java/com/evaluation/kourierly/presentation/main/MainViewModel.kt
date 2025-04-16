package com.evaluation.kourierly.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.kourierly.domain.repository.CustomerDetailsRepository
import com.evaluation.kourierly.navigation.Screen
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    private val customerDetailsRepository: CustomerDetailsRepository,
) : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            val customerDetails = customerDetailsRepository.customerDetailsFlow.firstOrNull()
            uiState =
                uiState.copy(
                    loading = false,
                    startDestination =
                        if (customerDetails?.customerName.isNullOrEmpty()) {
                            Screen.SendOtp
                        } else {
                            Screen.Home(customerDetails.customerName)
                        },
                )
        }
    }
}

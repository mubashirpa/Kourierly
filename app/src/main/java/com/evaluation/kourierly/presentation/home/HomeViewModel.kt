package com.evaluation.kourierly.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.kourierly.domain.repository.CustomerDetailsRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val customerDetailsRepository: CustomerDetailsRepository,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        customerDetails()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.Logout -> {
                viewModelScope.launch {
                    customerDetailsRepository.clearCustomerDetails()
                    uiState = uiState.copy(logoutSuccess = true)
                }
            }
        }
    }

    private fun customerDetails() {
        viewModelScope.launch {
            customerDetailsRepository.customerDetailsFlow.collect {
                uiState =
                    uiState.copy(
                        gender = it.gender.toString(),
                        phoneNumber = it.phoneNumber.toString(),
                        roleName = it.roleName.toString(),
                    )
            }
        }
    }
}

package com.evaluation.kourierly.presentation.customerUpdate

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import com.evaluation.kourierly.navigation.Screen
import kotlinx.coroutines.launch

class CustomerUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val kourierlyRepository: KourierlyRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CustomerUpdateUiState())
        private set

    private val args = savedStateHandle.toRoute<Screen.CustomerUpdate>()

    fun onEvent(event: CustomerUpdateUiEvent) {
        when (event) {
            is CustomerUpdateUiEvent.UpdateCustomer -> {
                updateCustomer(
                    name = event.name,
                    phoneNumber = args.phoneNumber,
                    roleId = args.roleId,
                    gender = event.gender,
                    customerId = args.customerId,
                )
            }

            CustomerUpdateUiEvent.UserMessageShown -> {
                uiState = uiState.copy(userMessage = null)
            }
        }
    }

    private fun updateCustomer(
        name: String,
        phoneNumber: String,
        roleId: String,
        gender: String,
        customerId: String,
    ) {
        if (name.isEmpty()) {
            uiState = uiState.copy(userMessage = "Name cannot be empty")
            return
        }

        if (gender.isEmpty()) {
            uiState = uiState.copy(userMessage = "Gender cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val result =
                    kourierlyRepository.mobileUpdate(
                        customerId = customerId,
                        customerName = name,
                        gender = gender,
                        phoneNumber = phoneNumber,
                        roleId = roleId,
                    )
                Log.d("hello", "updateCustomer: $result")
                val success = result.success == true
                uiState =
                    uiState.copy(
                        customerUpdateSuccess = success,
                        loading = false,
                        userMessage = if (success) null else result.message,
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState =
                    uiState.copy(
                        loading = false,
                        userMessage = e.message,
                    )
            }
        }
    }
}

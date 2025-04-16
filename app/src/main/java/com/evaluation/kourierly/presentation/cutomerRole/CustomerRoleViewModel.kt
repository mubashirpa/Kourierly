package com.evaluation.kourierly.presentation.cutomerRole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.kourierly.domain.repository.KourierlyRepository
import kotlinx.coroutines.launch

class CustomerRoleViewModel(
    private val kourierlyRepository: KourierlyRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CustomerRoleUiState())
        private set

    init {
        customerRolesList()
    }

    fun onEvent(event: CustomerRoleUiEvent) {
        when (event) {
            CustomerRoleUiEvent.OnContinueClicked -> {
                if (uiState.selectedRoleId == null) {
                    uiState = uiState.copy(userMessage = "Please select a role")
                }
            }

            is CustomerRoleUiEvent.OnRoleSelected -> {
                uiState = uiState.copy(selectedRoleId = event.roleId)
            }

            CustomerRoleUiEvent.UserMessageShown -> {
                uiState = uiState.copy(userMessage = null)
            }
        }
    }

    private fun customerRolesList() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                val result = kourierlyRepository.customerRoleList()
                val success = result.success == true
                uiState =
                    uiState.copy(
                        loading = false,
                        roles = result.data.orEmpty(),
                        success = success,
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

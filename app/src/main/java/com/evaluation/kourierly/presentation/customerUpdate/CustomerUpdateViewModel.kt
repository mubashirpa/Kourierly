package com.evaluation.kourierly.presentation.customerUpdate

import androidx.compose.foundation.text.input.TextFieldState
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

    init {
        customerRolesList()
    }

    fun onEvent(event: CustomerUpdateUiEvent) {
        when (event) {
            is CustomerUpdateUiEvent.OnGenderDropdownExpandedChange -> {
                uiState = uiState.copy(expandedGenderDropdown = event.expanded)
            }

            is CustomerUpdateUiEvent.OnRolesDropdownExpandedChange -> {
                uiState = uiState.copy(expandedRolesDropdown = event.expanded)
            }

            is CustomerUpdateUiEvent.UpdateCustomer -> {
                val roleId =
                    uiState.roles.firstOrNull { it.customerRole == uiState.roleState.text }?.roleId
                updateCustomer(
                    name = event.name.trim(),
                    phoneNumber = args.phoneNumber,
                    roleId = roleId?.toString(),
                    gender = event.gender,
                    customerId = args.customerId,
                )
            }

            CustomerUpdateUiEvent.UserMessageShown -> {
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
                val roles = result.data.orEmpty()
                val role = roles.firstOrNull()?.customerRole?.toString()
                uiState =
                    uiState.copy(
                        loading = false,
                        roleState = TextFieldState(role.orEmpty()),
                        roles = roles,
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

    private fun updateCustomer(
        name: String,
        phoneNumber: String,
        roleId: String?,
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

        if (roleId == null) {
            uiState = uiState.copy(userMessage = "Role cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                uiState = uiState.copy(openProgressDialog = true)
                val result =
                    kourierlyRepository.mobileUpdate(
                        customerId = customerId,
                        customerName = name,
                        gender = gender,
                        phoneNumber = phoneNumber,
                        roleId = roleId,
                    )
                val success = result.success == true
                uiState =
                    uiState.copy(
                        customerUpdateSuccess = success,
                        openProgressDialog = false,
                        userMessage = if (success) null else result.message,
                    )
            } catch (e: Exception) {
                e.printStackTrace()
                uiState =
                    uiState.copy(
                        openProgressDialog = false,
                        userMessage = e.message,
                    )
            }
        }
    }
}

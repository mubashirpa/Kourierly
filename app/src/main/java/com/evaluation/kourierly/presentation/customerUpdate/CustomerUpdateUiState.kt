package com.evaluation.kourierly.presentation.customerUpdate

import androidx.compose.foundation.text.input.TextFieldState
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleDataDto

data class CustomerUpdateUiState(
    val customerUpdateSuccess: Boolean = false,
    val expandedGenderDropdown: Boolean = false,
    val expandedRolesDropdown: Boolean = false,
    val genderList: List<String> = listOf("Male", "Female"),
    val genderState: TextFieldState = TextFieldState(),
    val loading: Boolean = false,
    val nameState: TextFieldState = TextFieldState(),
    val openProgressDialog: Boolean = false,
    val roleState: TextFieldState = TextFieldState(),
    val roles: List<CustomerRoleDataDto> = emptyList(),
    val selectedRoleState: TextFieldState = TextFieldState(),
    val success: Boolean = false,
    val userMessage: String? = null,
)

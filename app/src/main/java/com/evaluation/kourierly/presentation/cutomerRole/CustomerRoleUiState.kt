package com.evaluation.kourierly.presentation.cutomerRole

import androidx.compose.foundation.text.input.TextFieldState
import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleDataDto

data class CustomerRoleUiState(
    val loading: Boolean = false,
    val roles: List<CustomerRoleDataDto> = emptyList(),
    val selectedRole: TextFieldState = TextFieldState(),
    val selectedRoleId: Int? = null,
    val success: Boolean = false,
    val userMessage: String? = null,
)

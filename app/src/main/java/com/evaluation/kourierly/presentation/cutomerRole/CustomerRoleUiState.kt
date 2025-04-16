package com.evaluation.kourierly.presentation.cutomerRole

import com.evaluation.kourierly.data.remote.dto.roleList.CustomerRoleDataDto

data class CustomerRoleUiState(
    val loading: Boolean = false,
    val roles: List<CustomerRoleDataDto> = emptyList(),
    val success: Boolean = false,
    val userMessage: String? = null,
)

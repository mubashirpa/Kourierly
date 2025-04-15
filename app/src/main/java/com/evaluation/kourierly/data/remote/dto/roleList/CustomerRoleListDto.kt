package com.evaluation.kourierly.data.remote.dto.roleList

import kotlinx.serialization.Serializable

@Serializable
data class CustomerRoleListDto(
    val `data`: List<CustomerRoleDataDto>? = null,
    val message: String? = null,
    val success: Boolean? = null,
)

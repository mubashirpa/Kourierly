package com.evaluation.kourierly.data.remote.dto.roleList

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerRoleDataDto(
    val active: Int? = null,
    val createdAt: String? = null,
    @SerialName("customer_role")
    val customerRole: String? = null,
    val description: String? = null,
    val image: String? = null,
    @SerialName("role_id")
    val roleId: Int? = null,
    val updatedAt: String? = null,
)
